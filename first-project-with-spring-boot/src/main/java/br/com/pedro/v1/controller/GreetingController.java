/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.v1.controller;

import br.com.pedro.model.entity.Greeting;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@Hidden
@RestController
@RequestMapping("/api/v1/greeting")
public class GreetingController {
    
    private static final String TEMPLATE = "Hello %s!";
    private final AtomicLong counter = new AtomicLong(0);
    
    // GET
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Greeting getGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.getAndIncrement(), String.format(TEMPLATE, name));
    }
}
