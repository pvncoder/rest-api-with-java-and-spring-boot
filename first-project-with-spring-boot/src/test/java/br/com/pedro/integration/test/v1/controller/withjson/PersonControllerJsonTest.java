package br.com.pedro.integration.test.v1.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.pedro.config.TestConfig;
import br.com.pedro.integration.test.testcontainer.AbstractIntegrationTest;
import br.com.pedro.model.v1.dto.PersonDTO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Disabled;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

    // Variables
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonDTO personDTO;

    // General
    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // It won't fail with unknown properties on JSON (like the HATEOAS "_links")
        personDTO = new PersonDTO();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockPersonDTO();
        // Specification
        specification = new RequestSpecBuilder()
            .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_PEDRO)
            .setBasePath("/api/v1/persons")
            .setPort(TestConfig.SERVER_PORT)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();
        // Request
        var content = given()
            .spec(specification)
            .contentType(TestConfig.CONTENT_TYPE_JSON)
            .body(personDTO)
            .when().post()
            .then().statusCode(HttpStatus.OK.value())
            .extract().body().asString();
        // Test parameters
        PersonDTO createdPersonDTO = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPersonDTO;
        // Test
        assertNotNull(createdPersonDTO);
        assertNotNull(createdPersonDTO.getId());
        assertNotNull(createdPersonDTO.getFirstName());
        assertNotNull(createdPersonDTO.getLastName());
        assertNotNull(createdPersonDTO.getAddress());
        assertNotNull(createdPersonDTO.getGender());
        assertTrue(createdPersonDTO.getId() > 0);
        assertEquals("Pedro Vitor", createdPersonDTO.getFirstName());
        assertEquals("Nunes Arruda", createdPersonDTO.getLastName());
        assertEquals("Cascavel, Paraná, BR", createdPersonDTO.getAddress());
        assertEquals("Male", createdPersonDTO.getGender());
    }
    
    @Test
    @Order(2)
    public void testFindById() throws JsonProcessingException {
        // Specification
        specification = new RequestSpecBuilder()
            .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_PEDRO)
            .setBasePath("/api/v1/persons")
            .setPort(TestConfig.SERVER_PORT)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();
        // Request
        var content = given()
            .spec(specification)
            .contentType(TestConfig.CONTENT_TYPE_JSON)
            .pathParam("idPerson", personDTO.getId())
            .when().get("{idPerson}")
            .then().statusCode(HttpStatus.OK.value())
            .extract().body().asString();
        // Test parameters
        PersonDTO personFoundDTO = objectMapper.readValue(content, PersonDTO.class);
        personDTO = personFoundDTO;
        // Test
        assertNotNull(personFoundDTO);
        assertNotNull(personFoundDTO.getId());
        assertNotNull(personFoundDTO.getFirstName());
        assertNotNull(personFoundDTO.getLastName());
        assertNotNull(personFoundDTO.getAddress());
        assertNotNull(personFoundDTO.getGender());
        assertTrue(personFoundDTO.getId() > 0);
        assertEquals("Pedro Vitor", personFoundDTO.getFirstName());
        assertEquals("Nunes Arruda", personFoundDTO.getLastName());
        assertEquals("Cascavel, Paraná, BR", personFoundDTO.getAddress());
        assertEquals("Male", personFoundDTO.getGender());
    }

    private void mockPersonDTO() {
        personDTO.setFirstName("Pedro Vitor");
        personDTO.setLastName("Nunes Arruda");
        personDTO.setAddress("Cascavel, Paraná, BR");
        personDTO.setGender("Male");
    }
}
