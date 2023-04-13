/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.integration.test.model.v1.dto.security;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@XmlRootElement
public class AccountCredentialDTO implements Serializable {
    
    // Variables
    private String username;
    private String password;
    
    // Constructors
    public AccountCredentialDTO() {}

    public AccountCredentialDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    // Equals and hashcode
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.username);
        hash = 83 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountCredentialDTO other = (AccountCredentialDTO) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return Objects.equals(this.password, other.password);
    }
}
