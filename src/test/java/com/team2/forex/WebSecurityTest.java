package com.team2.forex;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT, classes ={ForexApplication.class})
public class WebSecurityTest {
	@Value("${server.port}")
	private int serverPort;
	
	@Before
	public void init(){
		RestAssured.port = serverPort;
	}
	
	@Test
	@PreAuthorize("authenticated")
	public void testH2AdminAccess(){
		given()
		.auth().basic("admin", "admin")
		.when().get("/h2")
		.then().statusCode(HttpStatus.SC_OK);
	}
	
	@Test
	public void testH2NonAdminAccess(){
		given()
		.auth().basic("client", "client")
		.when().get("/h2")
		.then().statusCode(HttpStatus.SC_FORBIDDEN);
	}
	
	@Test
	public void testUserAccess(){
		Response response = given()
		.auth().basic("client", "client")
		.when().get("/helloworld")
		.then().statusCode(HttpStatus.SC_OK)
		.and().extract().response();
		
		assertEquals("Response is incorrect", "helloworld", response.asString());
	}
	
	@Test
	public void testInvalidUserAccess(){
		given()
		.auth().basic("client", "client2")
		.when().get("/helloworld")
		.then().statusCode(HttpStatus.SC_UNAUTHORIZED);
	}
}
