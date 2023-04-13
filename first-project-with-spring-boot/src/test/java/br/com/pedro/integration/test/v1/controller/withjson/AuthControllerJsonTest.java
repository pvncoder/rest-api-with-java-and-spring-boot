package br.com.pedro.integration.test.v1.controller.withjson;

import br.com.pedro.config.TestConfig;
import br.com.pedro.integration.test.model.v1.dto.security.AccountCredentialDTO;
import br.com.pedro.integration.test.model.v1.dto.security.TokenDTO;
import br.com.pedro.integration.test.testcontainer.AbstractIntegrationTest;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerJsonTest extends AbstractIntegrationTest {

    // Variables
    private static TokenDTO tokenDTO;

    // General
    @Test
    @Order(1)
    public void testSignIn() throws JsonMappingException, JsonProcessingException {
        AccountCredentialDTO user = new AccountCredentialDTO("pedro", "admin123");
        tokenDTO = given()
            .basePath("/auth/sign-in")
            .port(TestConfig.SERVER_PORT)
            .contentType(TestConfig.CONTENT_TYPE_JSON)
            .body(user)
            .when()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(TokenDTO.class);
        assertNotNull(tokenDTO.getAccessToken());
        assertNotNull(tokenDTO.getRefreshToken());
    }

    @Test
    @Order(2)
    public void testRefresh() throws JsonMappingException, JsonProcessingException {
        var newTokenVO = given()
            .basePath("/auth/refresh")
            .port(TestConfig.SERVER_PORT)
            .contentType(TestConfig.CONTENT_TYPE_JSON)
            .pathParam("username", tokenDTO.getUsername())
            .header(TestConfig.HEADER_PARAM_AUTHORIZATION, String.format("Bearer %s", tokenDTO.getRefreshToken()))
            .when()
            .put("{username}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(TokenDTO.class);
        assertNotNull(newTokenVO.getAccessToken());
        assertNotNull(newTokenVO.getRefreshToken());
    }
}
