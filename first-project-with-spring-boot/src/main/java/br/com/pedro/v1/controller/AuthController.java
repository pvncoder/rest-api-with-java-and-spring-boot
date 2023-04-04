/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.v1.controller;

import br.com.pedro.model.v1.dto.security.AccountCredentialDTO;
import br.com.pedro.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import static java.util.Objects.isNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // General
    @Operation(summary = "Authenticates a user and returns a token")
    @PostMapping(value = "/sign-in")
    public ResponseEntity signIn(@RequestBody AccountCredentialDTO data) {
        if (checkIfParamsIsNotNull(data)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }
        var token = authService.signIn(data);
        if (isNull(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }
        return token;
    }
    
	@Operation(summary = "Refresh token for authenticated user and returns a token")
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
		if (checkIfParamsIsNotNull(username, refreshToken)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }
		var token = authService.refreshToken(username, refreshToken);
		if (isNull(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }
		return token;
	}

	private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
		return isNull(refreshToken) || refreshToken.isBlank() || isNull(username) || username.isBlank();
	}

    private boolean checkIfParamsIsNotNull(AccountCredentialDTO data) {
        return isNull(data) || isNull(data.getUsername()) || data.getUsername().isBlank()
            || isNull(data.getPassword()) || data.getPassword().isBlank();
    }
}
