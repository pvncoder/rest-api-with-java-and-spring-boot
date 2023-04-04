package br.com.pedro.unit.test.service;

import br.com.pedro.exception.RequiredObjectIsNullException;
import br.com.pedro.model.v1.dto.PersonDTO;
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

        var result = personService.findById(1L);
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

        PersonDTO personDTO = mockPerson.mockDTO(1);
        personDTO.setId(1L);

        when(personRepository.save(entity)).thenReturn(persisted);

        var result = personService.create(personDTO);

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
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> personService.create(null));

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdate() {
        Person entity = mockPerson.mockEntity(1);

        Person persisted = entity;
        persisted.setId(1L);

        PersonDTO dto = mockPerson.mockDTO(1);
        dto.setId(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(personRepository.save(entity)).thenReturn(persisted);

        var result = personService.update(dto);

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
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> personService.update(null));

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDelete() {
        Person entity = mockPerson.mockEntity(1);
        entity.setId(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));

        personService.delete(1L);
    }

    @Test
    void testFindAll() {
        List<Person> list = mockPerson.mockEntityList();

        when(personRepository.findAll()).thenReturn(list);

        var people = personService.findAll();

        assertNotNull(people);
        assertEquals(5, people.size());
        
        for (int i = 0; i < people.size(); i++) {
            PersonDTO personDTO = people.get(i);
            
            assertNotNull(personDTO);
            assertNotNull(personDTO.getId());
            assertNotNull(personDTO.getLinks());

            String link = "links: [</api/v1/persons/".concat(String.valueOf(personDTO.getId())).concat(">;rel=\"self\"]");
            assertTrue(personDTO.toString().contains(link));
            assertEquals("Address Test".concat(Integer.toString(i)), personDTO.getAddress());
            assertEquals("First Name Test".concat(Integer.toString(i)), personDTO.getFirstName());
            assertEquals("Last Name Test".concat(Integer.toString(i)), personDTO.getLastName());
            assertEquals(((personDTO.getId() % 2) == 0) ? "Male" : "Female", personDTO.getGender());
        }
    }
}
