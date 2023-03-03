/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.controller;

import br.com.pedro.model.v1.dto.PersonDTO;
import br.com.pedro.service.PersonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@RequestMapping("/persons")
@RestController
public class PersonController {

    @Autowired // Annotation to inject a class
    private PersonService personService;

    // GET
    @GetMapping(value = "/{idPerson}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO findById(@PathVariable(name = "idPerson") Long idPerson) {
        return personService.findById(idPerson);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonDTO> findAll() {
        return personService.findAll();
    }

    // POST
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO create(@RequestBody PersonDTO person) {
        return personService.create(person);
    }

    // PUT
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO update(@RequestBody PersonDTO person) {
        return personService.update(person);
    }

    // DELETE
    @DeleteMapping(value = "/{idPerson}")
    public ResponseEntity<?> delete(@PathVariable(name = "idPerson") Long idPerson) {
        personService.delete(idPerson);
        return ResponseEntity.noContent().build();
    }
}
