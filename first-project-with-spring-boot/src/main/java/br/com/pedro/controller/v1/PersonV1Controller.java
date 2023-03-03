/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.controller.v1;

import br.com.pedro.model.dto.v1.PersonV1DTO;
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
@RequestMapping("/api/v1/persons")
@RestController
public class PersonV1Controller {

    @Autowired // Annotation to inject a class
    private PersonService personService;

    // GET
    @GetMapping(value = "/{idPerson}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonV1DTO findById(@PathVariable(name = "idPerson") Long idPerson) {
        return personService.findByIdV1(idPerson);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonV1DTO> findAll() {
        return personService.findAllV1();
    }

    // POST
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonV1DTO create(@RequestBody PersonV1DTO person) {
        return personService.createV1(person);
    }

    // PUT
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonV1DTO update(@RequestBody PersonV1DTO person) {
        return personService.updateV1(person);
    }

    // DELETE
    @DeleteMapping(value = "/{idPerson}")
    public ResponseEntity<?> delete(@PathVariable(name = "idPerson") Long idPerson) {
        personService.deleteV1(idPerson);
        return ResponseEntity.noContent().build();
    }
}
