/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RequiredObjectIsNullException extends RuntimeException {

    // Constructor
    public RequiredObjectIsNullException() {
        super("It is not allowed to persist a null object");
    }
    
    public RequiredObjectIsNullException(String message) {
        super(message);
    }
}
