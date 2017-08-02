package com.team2.forex;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import javax.xml.ws.Response;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.team2.forex.entity.Currency;
import com.team2.forex.repository.MarketOrderRepository;
import com.team2.forex.service.MarketOrderService;

import io.restassured.RestAssured;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceCancelOrderTests {

	@Autowired
	private MarketOrderService MktOrderSev;

	@Autowired
	private MarketOrderRepository MktOrderRepo;
	
	@Value("${local.server.port}")
	private int serverPort;
	
	@Before
	public void init(){
		 RestAssured.port = serverPort;
	}
/*	
	@Test
    public void aCarGoesIntoTheGarage() {
        Map<String,String> car = new HashMap<>();
        car.put("plateNumber", "xyx1111");
        car.put("brand", "audi");
        car.put("colour", "red");

        given()
        .contentType("application/json")
        .body(car)
        .when().post("/garage/slots").then()
        .statusCode(200);
    }
    
    */
}
