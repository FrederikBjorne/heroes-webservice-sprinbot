package com.example.demo;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.preemptive;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DemoApplication.class }, webEnvironment = RANDOM_PORT)
public class HeroControllerTests {

	@Value("${local.server.port}")
	private int port;

	@Before
	public void setUp() {
        RestAssured.authentication = preemptive().basic("admin", "passw0rd");
	}

	private void checkOneHeroDetails(String restUrl) {
        get(restUrl)
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", Matchers.equalTo("Batman"))
                .and()
                .body("alterEgo", Matchers.equalTo("Bruce Wayne"))
                .and()
                .body("details", Matchers.hasEntry("eye", "Blue Eyes"))
                .and()
                .body("details", Matchers.hasEntry("hair", "Black Hair"))
                .and()
                .body("details", Matchers.hasEntry("page_id", "1422"))
                .and()
                .body("details", Matchers.hasEntry("gsm", ""))
                .and()
                .body("details", Matchers.hasEntry("sex", "Male Characters"))
                .and()
                .body("details", Matchers.hasEntry("alive", "Living Characters"))
                .and()
                .body("details", Matchers.hasEntry("align", "Good Characters"))
                .and()
                .body("details", Matchers.hasEntry("apperances", "3093"))
                .and()
                .body("details", Matchers.hasEntry("apperance", "\"1939, May\""))
                .and()
                .body("details", Matchers.hasEntry("urlslug", "\\/wiki\\/Batman_(Bruce_Wayne)"))
                .and()
                .body("id", Matchers.equalTo(1));
    }

	@Test
	public void verifyGetHeroAllInformationById() {
        String restUrl = "http://localhost:" + port + "/api/hero/:1";
        System.out.println(restUrl);
        checkOneHeroDetails(restUrl);
	}

    @Test
    public void verifyGetHeroAllInformationByName() {
        String restUrl = "http://localhost:" + port + "/api/hero/Batman";
        System.out.println(restUrl);
        checkOneHeroDetails(restUrl);
    }

    @Test
    public void verifyGetAllHeroesWithExtraShouldFail() {
        String restUrl = "http://localhost:" + port + "/api/heroes/Batman";
        System.out.println(restUrl);
        get(restUrl)
                .then()
                .assertThat()
                .statusCode(500);
    }
}
