/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.service;

import br.com.pedro.v1.controller.PersonController;
import br.com.pedro.exception.RequiredObjectIsNullException;
import br.com.pedro.exception.ResourceNotFoundException;
import br.com.pedro.mapper.DozerMapper;
import br.com.pedro.model.entity.Person;
import br.com.pedro.model.repository.PersonRepository;
import br.com.pedro.model.v1.dto.PersonDTO;
import jakarta.transaction.Transactional;
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
    public PersonDTO create(PersonDTO personDTO) {
        LOGGER.info("Creating one person");
        if (isNull(personDTO)) {
            throw new RequiredObjectIsNullException();
        }
        Person personToSave = DozerMapper.parseObject(personDTO, Person.class);
        Person savedPerson = personRepository.save(personToSave);
        PersonDTO personDTOSaved = DozerMapper.parseObject(savedPerson, PersonDTO.class);
        addCreateIdHATEOS(personDTOSaved);
        return personDTOSaved;
    }
    
    public PersonDTO update(PersonDTO personDTO) {
        LOGGER.info("Updating one person");
        if (isNull(personDTO)) {
            throw new RequiredObjectIsNullException();
        }
        Person personEntity = personRepository.findById(personDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personEntity.updateValues(personDTO);
        Person savedPerson = personRepository.save(personEntity);
        PersonDTO personDTOUpdated = DozerMapper.parseObject(savedPerson, PersonDTO.class);
        addUpdateHATEOS(personDTOUpdated);
        return personDTOUpdated;
    }
    
    @Transactional
	public PersonDTO disable(Long id) {
		LOGGER.info("Disabling one person");
		personRepository.disablePerson(id);
		Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		PersonDTO vo = DozerMapper.parseObject(entity, PersonDTO.class);
        addFindByIdHATEOS(id, vo);
		return vo;
	}
    
    public void delete(Long idPerson) {
        LOGGER.info("Deleting one person");
        Person personEntity = personRepository.findById(idPerson).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personRepository.delete(personEntity);
    }
    
    public PersonDTO findById(Long idPerson) {
        LOGGER.info("Finding one person");
        PersonDTO personDTO = DozerMapper.parseObject(personRepository.findById(idPerson)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID")), PersonDTO.class);
        addFindByIdHATEOS(idPerson, personDTO);
        return personDTO;
    }
    
    public List<PersonDTO> findAll() {
        LOGGER.info("Finding all persons");
        List<PersonDTO> personsDTO = DozerMapper.parseListObjects(personRepository.findAll(), PersonDTO.class);
        addAllHATEOS(personsDTO);
        return personsDTO;
    }
    
    // Links
    private void addUpdateHATEOS(PersonDTO personDTO) {
        addFindByIdHATEOS(personDTO.getId(), personDTO);
    }
    
    private void addCreateIdHATEOS(PersonDTO personDTO) {
        addFindByIdHATEOS(personDTO.getId(), personDTO);
    }
    
    private void addAllHATEOS(List<PersonDTO> personsDTO) {
        if (CollectionUtils.isNotEmpty(personsDTO)) {
            personsDTO.forEach(personDTO -> addFindByIdHATEOS(personDTO.getId(), personDTO));
        }
    }
    
    private void addFindByIdHATEOS(Long id, PersonDTO personDTO) {
        personDTO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
    }
}
