package br.com.pedro.integration.test.controller.withjson.v1;

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
import br.com.pedro.model.dto.v1.PersonV1DTO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonV1ControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonV1DTO personV1DTO;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // It won't fail with unknown properties on JSON (like the HATEOAS "_links")
        personV1DTO = new PersonV1DTO();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockPersonV1DTO();
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
            .body(personV1DTO)
            .when().post()
            .then().statusCode(HttpStatus.OK.value())
            .extract().body().asString();
        // Test parameters
        PersonV1DTO createdPersonV1DTO = objectMapper.readValue(content, PersonV1DTO.class);
        personV1DTO = createdPersonV1DTO;
        // Test
        assertNotNull(createdPersonV1DTO);
        assertNotNull(createdPersonV1DTO.getId());
        assertNotNull(createdPersonV1DTO.getFirstName());
        assertNotNull(createdPersonV1DTO.getLastName());
        assertNotNull(createdPersonV1DTO.getAddress());
        assertNotNull(createdPersonV1DTO.getGender());
        assertTrue(createdPersonV1DTO.getId() > 0);
        assertEquals("Pedro Vitor", createdPersonV1DTO.getFirstName());
        assertEquals("Nunes Arruda", createdPersonV1DTO.getLastName());
        assertEquals("Cascavel, Paraná, BR", createdPersonV1DTO.getAddress());
        assertEquals("Male", createdPersonV1DTO.getGender());
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
            .pathParam("idPerson", personV1DTO.getId())
            .when().get("{idPerson}")
            .then().statusCode(HttpStatus.OK.value())
            .extract().body().asString();
        // Test parameters
        PersonV1DTO personFoundV1DTO = objectMapper.readValue(content, PersonV1DTO.class);
        personV1DTO = personFoundV1DTO;
        // Test
        assertNotNull(personFoundV1DTO);
        assertNotNull(personFoundV1DTO.getId());
        assertNotNull(personFoundV1DTO.getFirstName());
        assertNotNull(personFoundV1DTO.getLastName());
        assertNotNull(personFoundV1DTO.getAddress());
        assertNotNull(personFoundV1DTO.getGender());
        assertTrue(personFoundV1DTO.getId() > 0);
        assertEquals("Pedro Vitor", personFoundV1DTO.getFirstName());
        assertEquals("Nunes Arruda", personFoundV1DTO.getLastName());
        assertEquals("Cascavel, Paraná, BR", personFoundV1DTO.getAddress());
        assertEquals("Male", personFoundV1DTO.getGender());
    }

    private void mockPersonV1DTO() {
        personV1DTO.setFirstName("Pedro Vitor");
        personV1DTO.setLastName("Nunes Arruda");
        personV1DTO.setAddress("Cascavel, Paraná, BR");
        personV1DTO.setGender("Male");
    }
}
