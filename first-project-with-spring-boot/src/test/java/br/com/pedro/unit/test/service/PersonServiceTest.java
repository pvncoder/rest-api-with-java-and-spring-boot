package br.com.pedro.unit.test.service;

import br.com.pedro.exception.RequiredObjectIsNullException;
import br.com.pedro.model.dto.v1.PersonV1DTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pedro.model.entity.Person;
import br.com.pedro.model.repository.PersonRepository;
import br.com.pedro.service.PersonService;
import br.com.pedro.unit.test.mapper.mock.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    private MockPerson mockPerson;

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setUpMocks() throws Exception {
        mockPerson = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Person entity = mockPerson.mockEntity(1);
        entity.setId(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));

        var result = personService.findByIdV1(1L);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/v1/persons/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testCreate() {
        Person entity = mockPerson.mockEntity(1);
        entity.setId(1L);

        Person persisted = entity;
        persisted.setId(1L);

        PersonV1DTO personV1DTO = mockPerson.mockDTO(1);
        personV1DTO.setId(1L);

        when(personRepository.save(entity)).thenReturn(persisted);

        var result = personService.createV1(personV1DTO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/v1/persons/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> personService.createV1(null));

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdate() {
        Person entity = mockPerson.mockEntity(1);

        Person persisted = entity;
        persisted.setId(1L);

        PersonV1DTO dto = mockPerson.mockDTO(1);
        dto.setId(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(personRepository.save(entity)).thenReturn(persisted);

        var result = personService.updateV1(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/v1/persons/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> personService.updateV1(null));

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDelete() {
        Person entity = mockPerson.mockEntity(1);
        entity.setId(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));

        personService.deleteV1(1L);
    }

    @Test
    void testFindAll() {
        List<Person> list = mockPerson.mockEntityList();

        when(personRepository.findAll()).thenReturn(list);

        var people = personService.findAllV1();

        assertNotNull(people);
        assertEquals(5, people.size());
        
        for (int i = 0; i < people.size(); i++) {
            PersonV1DTO personV1DTO = people.get(i);
            
            assertNotNull(personV1DTO);
            assertNotNull(personV1DTO.getId());
            assertNotNull(personV1DTO.getLinks());

            String link = "links: [</api/v1/persons/".concat(String.valueOf(personV1DTO.getId())).concat(">;rel=\"self\"]");
            assertTrue(personV1DTO.toString().contains(link));
            assertEquals("Address Test".concat(Integer.toString(i)), personV1DTO.getAddress());
            assertEquals("First Name Test".concat(Integer.toString(i)), personV1DTO.getFirstName());
            assertEquals("Last Name Test".concat(Integer.toString(i)), personV1DTO.getLastName());
            assertEquals(((personV1DTO.getId() % 2) == 0) ? "Male" : "Female", personV1DTO.getGender());
        }
    }
}
