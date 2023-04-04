/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.service;

import br.com.pedro.model.repository.UserRepository;
import br.com.pedro.model.v1.dto.security.AccountCredentialDTO;
import br.com.pedro.model.v1.dto.security.TokenDTO;
import br.com.pedro.security.jwt.JwtTokenProvider;
import static java.util.Objects.nonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@Service
public class AuthService {

    private final Logger LOGGER = LogManager.getLogger(AuthService.class);
    
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;
    
    // General
    public ResponseEntity signIn(AccountCredentialDTO data) {
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = userRepository.findByUsername(username);
            var tokenResponse = new TokenDTO();
            if (nonNull(user)) {
                tokenResponse = jwtTokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException(String.format("Username \"%s\" not found", username));
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception ex) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
    
	public ResponseEntity refreshToken(String username, String refreshToken) {
		var user = userRepository.findByUsername(username);
		var tokenResponse = new TokenDTO();
		if (nonNull(user)) {
			tokenResponse = jwtTokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException(String.format("Username \"%s\" not found", username));
		}
		return ResponseEntity.ok(tokenResponse);
	}
}
