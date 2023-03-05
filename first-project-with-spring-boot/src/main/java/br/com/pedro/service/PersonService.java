/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.service;

import br.com.pedro.exception.ResourceNotFoundException;
import br.com.pedro.mapper.DozerMapper;
import br.com.pedro.model.entity.Person;
import br.com.pedro.model.repository.PersonRepository;
import br.com.pedro.model.dto.v1.PersonV1DTO;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PersonV1DTO createV1(PersonV1DTO personDTO) {
        LOGGER.info("Creating one person");
        Person personToSave = DozerMapper.parseObject(personDTO, Person.class);
        Person savedPerson = personRepository.save(personToSave);
        return DozerMapper.parseObject(savedPerson, PersonV1DTO.class);
    }
    
    public PersonV1DTO updateV1(PersonV1DTO personDTO) {
        LOGGER.info("Updating one person");
        Person personEntity = personRepository.findById(personDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personEntity.updateValues(personDTO);
        Person savedPerson = personRepository.save(personEntity);
        return DozerMapper.parseObject(savedPerson, PersonV1DTO.class);
    }
    
    public void deleteV1(Long idPerson) {
        LOGGER.info("Deleting one person");
        Person personEntity = personRepository.findById(idPerson).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personRepository.delete(personEntity);
    }
    
    public PersonV1DTO findByIdV1(Long idPerson) {
        LOGGER.info("Finding one person");
        return DozerMapper.parseObject(personRepository.findById(idPerson).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID")),
            PersonV1DTO.class);
    }
    
    public List<PersonV1DTO> findAllV1() {
        LOGGER.info("Finding all persons");
        return DozerMapper.parseListObjects(personRepository.findAll(), PersonV1DTO.class);
    }
}
