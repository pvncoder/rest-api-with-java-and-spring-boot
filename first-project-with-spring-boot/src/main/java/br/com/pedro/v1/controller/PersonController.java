/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.v1.controller;

import br.com.pedro.model.v1.dto.PersonDTO;
import br.com.pedro.service.PersonService;
import br.com.pedro.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@Tag(name = "People", description = "Endpoints for Managing People")
@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    @Autowired // Annotation to inject a class
    private PersonService personService;

    // GET
    @Operation(
        summary = "Find a Person",
        description = "Find a Person",
        tags = {"People"},
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PersonDTO.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    @GetMapping(value = "/{idPerson}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML})
    public PersonDTO findById(@PathVariable(name = "idPerson") Long idPerson) {
        return personService.findById(idPerson);
    }

    @Operation(
        summary = "Find all People",
        description = "Find all People",
        tags = {"People"},
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))
                }
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML})
    public List<PersonDTO> findAll() {
        return personService.findAll();
    }

    // POST
    @Operation(
        summary = "Create a Person",
        description = "Create a Person",
        tags = {"People"},
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PersonDTO.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    @PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML}, produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML})
    public PersonDTO create(@RequestBody PersonDTO person) {
        return personService.create(person);
    }

    // PUT
    @Operation(
        summary = "Update a Person",
        description = "Update a Person",
        tags = {"People"},
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PersonDTO.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    @PutMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML}, produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML})
    public PersonDTO update(@RequestBody PersonDTO person) {
        return personService.update(person);
    }

    // PATCH
    @PatchMapping(value = "/{idPerson}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML})
    @Operation(
        summary = "Disable a specific Person by your ID",
        description = "Disable a specific Person by your ID",
        tags = {"People"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),}
    )
    public PersonDTO disablePerson(@PathVariable(value = "idPerson") Long idPerson) {
        return personService.disable(idPerson);
    }

    // DELETE
    @Operation(
        summary = "Delete a Person",
        description = "Delete a Person",
        tags = {"People"},
        responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    @DeleteMapping(value = "/{idPerson}")
    public ResponseEntity<?> delete(@PathVariable(name = "idPerson") Long idPerson) {
        personService.delete(idPerson);
        return ResponseEntity.noContent().build();
    }
}
