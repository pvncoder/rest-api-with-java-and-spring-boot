package br.com.pedro.integration.test.swagger;

import br.com.pedro.config.TestConfig;
import br.com.pedro.integration.test.testcontainer.AbstractIntegrationTest;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void shouldDisplaySwaggerUIPage() {
        var content = given()
            .basePath("/swagger-ui/index.html")
            .port(TestConfig.SERVER_PORT)
            .when().get()
            .then().statusCode(HttpStatus.OK.value())
            .extract().body().asString();
        assertTrue(content.contains("Swagger UI"));
	}
}
