package br.com.pedro.integration.test.v1.controller.withxml;

import br.com.pedro.config.TestConfig;
import br.com.pedro.integration.test.model.v1.dto.PersonDTO;
import br.com.pedro.integration.test.model.v1.dto.security.AccountCredentialDTO;
import br.com.pedro.integration.test.model.v1.dto.security.TokenDTO;
import br.com.pedro.integration.test.testcontainer.AbstractIntegrationTest;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerXmlTest extends AbstractIntegrationTest {

    // Variables
    private static PersonDTO person;
    private static RequestSpecification specification;
    private static XmlMapper objectMapper;

    // General
    @BeforeAll
    public static void setup() {
        objectMapper = new XmlMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        person = new PersonDTO();
    }

    @Test
    @Order(0)
    public void authorization() throws JsonMappingException, JsonProcessingException {
        AccountCredentialDTO user = new AccountCredentialDTO("pedro", "admin123");
        var accessToken = given()
            .basePath("/auth/sign-in")
            .port(TestConfig.SERVER_PORT)
            .contentType(TestConfig.CONTENT_TYPE_XML)
            .accept(TestConfig.CONTENT_TYPE_XML)
            .body(user)
            .when()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(TokenDTO.class)
            .getAccessToken();
        specification = new RequestSpecBuilder()
            .addHeader(TestConfig.HEADER_PARAM_AUTHORIZATION, String.format("Bearer %s", accessToken))
            .setBasePath("/api/v1/persons")
            .setPort(TestConfig.SERVER_PORT)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonMappingException, JsonProcessingException {
        mockPerson();
        var content = given().spec(specification)
            .contentType(TestConfig.CONTENT_TYPE_XML)
            .accept(TestConfig.CONTENT_TYPE_XML)
            .body(person)
            .when()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();
        PersonDTO persistedPerson = objectMapper.readValue(content, PersonDTO.class);
        person = persistedPerson;
        assertNotNull(persistedPerson);
        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertTrue(persistedPerson.getEnabled());
        assertTrue(persistedPerson.getId() > 0);
        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonMappingException, JsonProcessingException {
        person.setLastName("Piquet Souto Maior");
        var content = given().spec(specification)
            .contentType(TestConfig.CONTENT_TYPE_XML)
            .accept(TestConfig.CONTENT_TYPE_XML)
            .body(person)
            .when()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();
        PersonDTO persistedPerson = objectMapper.readValue(content, PersonDTO.class);
        person = persistedPerson;
        assertNotNull(persistedPerson);
        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertTrue(persistedPerson.getEnabled());
        assertEquals(person.getId(), persistedPerson.getId());
        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(3)
    public void testDisablePersonById() throws JsonMappingException, JsonProcessingException {
        var content = given().spec(specification)
            .contentType(TestConfig.CONTENT_TYPE_XML)
            .accept(TestConfig.CONTENT_TYPE_XML)
            .pathParam("id", person.getId())
            .when()
            .patch("{id}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();
        PersonDTO persistedPerson = objectMapper.readValue(content, PersonDTO.class);
        person = persistedPerson;
        assertNotNull(persistedPerson);
        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertFalse(persistedPerson.getEnabled());
        assertEquals(person.getId(), persistedPerson.getId());
        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(4)
    public void testFindById() throws JsonMappingException, JsonProcessingException {
        mockPerson();
        var content = given().spec(specification)
            .contentType(TestConfig.CONTENT_TYPE_XML)
            .accept(TestConfig.CONTENT_TYPE_XML)
            .pathParam("id", person.getId())
            .when()
            .get("{id}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();
        PersonDTO persistedPerson = objectMapper.readValue(content, PersonDTO.class);
        person = persistedPerson;
        assertNotNull(persistedPerson);
        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertFalse(persistedPerson.getEnabled());
        assertEquals(person.getId(), persistedPerson.getId());
        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(5)
    public void testDelete() throws JsonMappingException, JsonProcessingException {
        given().spec(specification)
            .contentType(TestConfig.CONTENT_TYPE_XML)
            .accept(TestConfig.CONTENT_TYPE_XML)
            .pathParam("id", person.getId())
            .when()
            .delete("{id}")
            .then()
            .statusCode(204);
    }

    @Test
    @Order(6)
    public void testFindAll() throws JsonMappingException, JsonProcessingException {
        var content = given().spec(specification)
            .contentType(TestConfig.CONTENT_TYPE_XML)
            .accept(TestConfig.CONTENT_TYPE_XML)
            .when()
            .get()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();
        List<PersonDTO> people = objectMapper.readValue(content, new TypeReference<List<PersonDTO>>() {});
        // Person 1
        PersonDTO foundPersonOne = people.get(0);
        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());
        assertTrue(foundPersonOne.getEnabled());
        assertEquals(1, foundPersonOne.getId());
        assertEquals("Ayrton", foundPersonOne.getFirstName());
        assertEquals("Senna", foundPersonOne.getLastName());
        assertEquals("São Paulo", foundPersonOne.getAddress());
        assertEquals("Male", foundPersonOne.getGender());
        // Person 6
        PersonDTO foundPersonSix = people.get(5);
        assertNotNull(foundPersonSix.getId());
        assertNotNull(foundPersonSix.getFirstName());
        assertNotNull(foundPersonSix.getLastName());
        assertNotNull(foundPersonSix.getAddress());
        assertNotNull(foundPersonSix.getGender());
        assertTrue(foundPersonSix.getEnabled());
        assertEquals(6, foundPersonSix.getId());
        assertEquals("Nelson", foundPersonSix.getFirstName());
        assertEquals("Mvezo", foundPersonSix.getLastName());
        assertEquals("Mvezo – South Africa", foundPersonSix.getAddress());
        assertEquals("Male", foundPersonSix.getGender());
    }

    @Test
    @Order(7)
    public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
        RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
            .setBasePath("/api/v1/person")
            .setPort(TestConfig.SERVER_PORT)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();
        given().spec(specificationWithoutToken)
            .contentType(TestConfig.CONTENT_TYPE_XML)
            .accept(TestConfig.CONTENT_TYPE_XML)
            .when()
            .get()
            .then()
            .statusCode(403);
    }

    private void mockPerson() {
        person.setFirstName("Nelson");
        person.setLastName("Piquet");
        person.setAddress("Brasília - DF - Brasil");
        person.setGender("Male");
        person.setEnabled(Boolean.TRUE);
    }
}
