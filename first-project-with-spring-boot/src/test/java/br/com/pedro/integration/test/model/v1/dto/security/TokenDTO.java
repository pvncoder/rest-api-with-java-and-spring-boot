/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.integration.test.model.v1.dto.security;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@XmlRootElement(name = "TokenDTO")
public class TokenDTO implements Serializable {
    
    // Variables
    private String username;
    private Boolean authenticated;
    private Date created;
    private Date expiration;
    private String accessToken;
    private String refreshToken;
    
    // Constructors
    public TokenDTO() {}
    
    public TokenDTO(String username, Boolean authenticated, Date created, Date expiration, String accessToken, String refreshToken) {
        this.username = username;
        this.authenticated = authenticated;
        this.created = created;
        this.expiration = expiration;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // Equals and hashcode
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.username);
        hash = 13 * hash + Objects.hashCode(this.authenticated);
        hash = 13 * hash + Objects.hashCode(this.created);
        hash = 13 * hash + Objects.hashCode(this.expiration);
        hash = 13 * hash + Objects.hashCode(this.accessToken);
        hash = 13 * hash + Objects.hashCode(this.refreshToken);
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
        final TokenDTO other = (TokenDTO) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.accessToken, other.accessToken)) {
            return false;
        }
        if (!Objects.equals(this.refreshToken, other.refreshToken)) {
            return false;
        }
        if (!Objects.equals(this.authenticated, other.authenticated)) {
            return false;
        }
        if (!Objects.equals(this.created, other.created)) {
            return false;
        }
        return Objects.equals(this.expiration, other.expiration);
    }
}
