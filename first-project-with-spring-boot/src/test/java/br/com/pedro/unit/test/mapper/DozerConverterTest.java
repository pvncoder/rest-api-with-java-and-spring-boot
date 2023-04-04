package br.com.pedro.unit.test.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.pedro.mapper.DozerMapper;
import br.com.pedro.model.entity.Person;
import br.com.pedro.model.v1.dto.PersonDTO;
import br.com.pedro.unit.test.mapper.mock.MockPerson;

public class DozerConverterTest {
    
    MockPerson inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockPerson();
    }

    @Test
    public void parseEntityToDTOTest() {
        PersonDTO output = DozerMapper.parseObject(inputObject.mockEntity(), PersonDTO.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Address Test0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseEntityListToDTOListTest() {
        List<PersonDTO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), PersonDTO.class);
        PersonDTO outputZero = outputList.get(0);
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Address Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());

        PersonDTO outputThree = outputList.get(3);
        assertEquals(Long.valueOf(3L), outputThree.getId());
        assertEquals("First Name Test3", outputThree.getFirstName());
        assertEquals("Last Name Test3", outputThree.getLastName());
        assertEquals("Address Test3", outputThree.getAddress());
        assertEquals("Female", outputThree.getGender());

        PersonDTO outputFour = outputList.get(4);
        assertEquals(Long.valueOf(4L), outputFour.getId());
        assertEquals("First Name Test4", outputFour.getFirstName());
        assertEquals("Last Name Test4", outputFour.getLastName());
        assertEquals("Address Test4", outputFour.getAddress());
        assertEquals("Male", outputFour.getGender());
    }

    @Test
    public void parseDTOToEntityTest() {
        Person output = DozerMapper.parseObject(inputObject.mockDTO(), Person.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Address Test0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parserDTOListToEntityListTest() {
        List<Person> outputList = DozerMapper.parseListObjects(inputObject.mockDTOList(), Person.class);
        Person outputZero = outputList.get(0);
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Address Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());
        
        Person outputTwo = outputList.get(2);
        assertEquals(Long.valueOf(2L), outputTwo.getId());
        assertEquals("First Name Test2", outputTwo.getFirstName());
        assertEquals("Last Name Test2", outputTwo.getLastName());
        assertEquals("Address Test2", outputTwo.getAddress());
        assertEquals("Male", outputTwo.getGender());
        
        Person outputFour = outputList.get(4);
        assertEquals(Long.valueOf(4L), outputFour.getId());
        assertEquals("First Name Test4", outputFour.getFirstName());
        assertEquals("Last Name Test4", outputFour.getLastName());
        assertEquals("Address Test4", outputFour.getAddress());
        assertEquals("Male", outputFour.getGender());
    }
}
