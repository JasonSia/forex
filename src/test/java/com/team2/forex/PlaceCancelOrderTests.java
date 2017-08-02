package com.team2.forex;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.Response;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.team2.forex.entity.Currency;
import com.team2.forex.repository.MarketOrderRepository;
import com.team2.forex.service.MarketOrderService;

import io.restassured.RestAssured;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT, classes = { ForexApplication.class})
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
	
	
	@Test
    public void aCarGoesIntoTheGarage() throws JSONException {
		JSONObject Order = new JSONObject();
		Order.put("orderType", "BUY");
        Order.put("currencyBuyInput", "HKD");
        Order.put("currencySellInput", "USD");
        Order.put("size", 120);
        Order.put("status", "NOTFILLED");

        given()
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/placeMarketOrder").then()
        .statusCode(200);
    }
    
}

