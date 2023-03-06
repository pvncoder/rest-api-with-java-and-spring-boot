/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.service;

import br.com.pedro.controller.v1.PersonV1Controller;
import br.com.pedro.exception.RequiredObjectIsNullException;
import br.com.pedro.exception.ResourceNotFoundException;
import br.com.pedro.mapper.DozerMapper;
import br.com.pedro.model.entity.Person;
import br.com.pedro.model.repository.PersonRepository;
import br.com.pedro.model.dto.v1.PersonV1DTO;
import java.util.List;
import static java.util.Objects.isNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@Service // Annotation to warn Spring which is an injectable class
public class PersonService {

    private final Logger LOGGER = LogManager.getLogger(PersonService.class);
    
    @Autowired
    private PersonRepository personRepository;
    
    // General
    public PersonV1DTO createV1(PersonV1DTO personV1DTO) {
        LOGGER.info("Creating one person");
        if (isNull(personV1DTO)) {
            throw new RequiredObjectIsNullException();
        }
        Person personToSave = DozerMapper.parseObject(personV1DTO, Person.class);
        Person savedPerson = personRepository.save(personToSave);
        PersonV1DTO personV1DTOSaved = DozerMapper.parseObject(savedPerson, PersonV1DTO.class);
        addCreateIdV1HATEOS(personV1DTOSaved);
        return personV1DTOSaved;
    }
    
    public PersonV1DTO updateV1(PersonV1DTO personV1DTO) {
        LOGGER.info("Updating one person");
        if (isNull(personV1DTO)) {
            throw new RequiredObjectIsNullException();
        }
        Person personEntity = personRepository.findById(personV1DTO.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personEntity.updateValues(personV1DTO);
        Person savedPerson = personRepository.save(personEntity);
        PersonV1DTO personV1DTOUpdated = DozerMapper.parseObject(savedPerson, PersonV1DTO.class);
        addUpdateV1HATEOS(personV1DTOUpdated);
        return personV1DTOUpdated;
    }
    
    public void deleteV1(Long idPerson) {
        LOGGER.info("Deleting one person");
        Person personEntity = personRepository.findById(idPerson).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personRepository.delete(personEntity);
    }
    
    public PersonV1DTO findByIdV1(Long idPerson) {
        LOGGER.info("Finding one person");
        PersonV1DTO personV1DTO = DozerMapper.parseObject(personRepository.findById(idPerson)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID")), PersonV1DTO.class);
        addFindByIdV1HATEOS(idPerson, personV1DTO);
        return personV1DTO;
    }
    
    public List<PersonV1DTO> findAllV1() {
        LOGGER.info("Finding all persons");
        List<PersonV1DTO> personsV1DTO = DozerMapper.parseListObjects(personRepository.findAll(), PersonV1DTO.class);
        addAllV1HATEOS(personsV1DTO);
        return personsV1DTO;
    }
    
    // Links
    private void addUpdateV1HATEOS(PersonV1DTO personV1DTO) {
        addFindByIdV1HATEOS(personV1DTO.getId(), personV1DTO);
    }
    
    private void addCreateIdV1HATEOS(PersonV1DTO personV1DTO) {
        addFindByIdV1HATEOS(personV1DTO.getId(), personV1DTO);
    }
    
    private void addAllV1HATEOS(List<PersonV1DTO> personsV1DTO) {
        if (CollectionUtils.isNotEmpty(personsV1DTO)) {
            personsV1DTO.forEach(personV1DTO -> addFindByIdV1HATEOS(personV1DTO.getId(), personV1DTO));
        }
    }
    
    private void addFindByIdV1HATEOS(Long id, PersonV1DTO personV1DTO) {
        personV1DTO.add(linkTo(methodOn(PersonV1Controller.class).findById(id)).withSelfRel());
    }
}
