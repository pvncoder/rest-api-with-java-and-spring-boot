/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.controller;

import br.com.pedro.model.entity.Greeting;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Pedro Vitor
 */
@RestController
public class GreetingController {
    
    private static final String TEMPLATE = "Hello %s!";
    private final AtomicLong counter = new AtomicLong(0);
    
    // GET
    @RequestMapping("/greeting")
    public Greeting getGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.getAndIncrement(), String.format(TEMPLATE, name));
    }
}
