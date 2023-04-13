package br.com.pedro.integration.test.v1.controller.withyaml;

import br.com.pedro.config.TestConfig;
import br.com.pedro.integration.test.model.v1.dto.security.AccountCredentialDTO;
import br.com.pedro.integration.test.model.v1.dto.security.TokenDTO;
import br.com.pedro.integration.test.testcontainer.AbstractIntegrationTest;
import br.com.pedro.integration.test.v1.controller.withyaml.mapper.YMLMapper;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerYamlTest extends AbstractIntegrationTest {

    // Variables
    private static YMLMapper objectMapper;
    private static TokenDTO tokenDTO;

    // General
    @BeforeAll
    public static void setup() {
        objectMapper = new YMLMapper();
    }

    @Test
    @Order(1)
    public void testSignin() throws JsonMappingException, JsonProcessingException {
        AccountCredentialDTO user = new AccountCredentialDTO("pedro", "admin123");
        RequestSpecification specification = new RequestSpecBuilder()
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();
        tokenDTO = given().spec(specification)
            .config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfig.CONTENT_TYPE_YML, ContentType.TEXT)))
            .accept(TestConfig.CONTENT_TYPE_YML)
            .basePath("/auth/sign-in")
            .port(TestConfig.SERVER_PORT)
            .contentType(TestConfig.CONTENT_TYPE_YML)
            .body(user, objectMapper)
            .when()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(TokenDTO.class, objectMapper);
        assertNotNull(tokenDTO.getAccessToken());
        assertNotNull(tokenDTO.getRefreshToken());
    }

    @Test
    @Order(2)
    public void testRefresh() throws JsonMappingException, JsonProcessingException {
        var newTokenVO = given()
            .config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(TestConfig.CONTENT_TYPE_YML, ContentType.TEXT)))
            .accept(TestConfig.CONTENT_TYPE_YML)
            .basePath("/auth/refresh")
            .port(TestConfig.SERVER_PORT)
            .contentType(TestConfig.CONTENT_TYPE_YML)
            .pathParam("username", tokenDTO.getUsername())
            .header(TestConfig.HEADER_PARAM_AUTHORIZATION, String.format("Bearer %s", tokenDTO.getRefreshToken()))
            .when()
            .put("{username}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(TokenDTO.class, objectMapper);
        assertNotNull(newTokenVO.getAccessToken());
        assertNotNull(newTokenVO.getRefreshToken());
    }
}
