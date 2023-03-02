/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.model.entity;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
public class Greeting {
    
    // Variables
    private Long id;
    private String content;

    // Constructor
    public Greeting(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    // Getters && Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
