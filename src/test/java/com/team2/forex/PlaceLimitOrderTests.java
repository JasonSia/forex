package com.team2.forex;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

//import javax.xml.ws.Response;

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
import io.restassured.response.Response;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT, classes = { ForexApplication.class})
public class PlaceLimitOrderTests {

	@Autowired
	private MarketOrderService los;

	@Autowired
	private MarketOrderRepository limitOrderRepo;
	
	
	@Value("${local.server.port}")
	private int serverPort;
	
	@Before
	public void init(){
		 RestAssured.port = serverPort;
	}
	
	
	@Test
    public void PlaceLimitOrderSuccess() throws JSONException {
		JSONObject Order = new JSONObject();
		Order.put("orderType", "LIMIT");
        Order.put("currencyBuy", "HKD");
        Order.put("currencySell", "USD");
        Order.put("preferredPrice", 20.50);
        Order.put("goodTillDate", "2020-07-30_23:04:13.0");
        Order.put("size", 120);

        given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/placeLimitOrder").then()
        .statusCode(200);
    }
	
	@Test
	public void PlaceLimitOrderInputWrongOrderType()throws JSONException{
		JSONObject Order = new JSONObject();
		Order.put("orderType", "random");
        Order.put("currencyBuy", "HKD");
        Order.put("currencySell", "USD");
        Order.put("preferredPrice", 20.50);
        Order.put("goodTillDate", "2020-07-30_23:04:13.0");
        Order.put("size", 120);
        
        Response response=given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/placeLimitOrder")
        .then().statusCode(HttpStatus.SC_OK)
        .and().extract().response();

		assertEquals("User input Order Type is incorrect", "ERROR: OrderType should only be Limit.", response.asString());
	}
	
	@Test
	public void PlaceLimitOrderInputWrongBuyCurrency()throws JSONException{
		JSONObject Order = new JSONObject();
		Order.put("orderType", "LIMIT");
        Order.put("currencyBuy", "INR");
        Order.put("currencySell", "USD");
        Order.put("preferredPrice", 20.50);
        Order.put("goodTillDate", "2020-07-30_23:04:13.0");
        Order.put("size", 120);
        
        Response response=given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/placeLimitOrder")
        .then().statusCode(HttpStatus.SC_OK)
        .and().extract().response();

		assertEquals("User input Buy Currency is not supported", "ERROR: Buy Currency not supported.", response.asString());
	}
    
	@Test
	public void PlaceLimitOrderInputWrongSellCurrency()throws JSONException{
		JSONObject Order = new JSONObject();
		Order.put("orderType", "LIMIT");
        Order.put("currencyBuy", "SGD");
        Order.put("currencySell", "INR");
        Order.put("preferredPrice", 20.50);
        Order.put("goodTillDate", "2020-07-30_23:04:13.0");
        Order.put("size", 120);
        
        Response response=given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/placeLimitOrder")
        .then().statusCode(HttpStatus.SC_OK)
        .and().extract().response();

		assertEquals("User input Sell Currency is not supported", "ERROR: Sell Currency not supported.", response.asString());
	}
	
	@Test
	public void PlaceLimitOrderInputWrongSize()throws JSONException{
		JSONObject Order = new JSONObject();
		Order.put("orderType", "LIMIT");
        Order.put("currencyBuy", "HKD");
        Order.put("currencySell", "USD");
        Order.put("preferredPrice", 20.50);
        Order.put("goodTillDate", "2020-07-30_23:04:13.0");
        Order.put("size", 0);
        
        Response response=given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/placeLimitOrder")
        .then().statusCode(HttpStatus.SC_OK)
        .and().extract().response();

		assertEquals("User input size is incorrect", "ERROR: The size should be a positive value greater than 0.", response.asString());
	}
	
	@Test
	public void PlaceLimitOrderInputWrongPreferredPrice()throws JSONException{
		JSONObject Order = new JSONObject();
		Order.put("orderType", "LIMIT");
        Order.put("currencyBuy", "HKD");
        Order.put("currencySell", "USD");
        Order.put("preferredPrice", 0);
        Order.put("goodTillDate", "2020-07-30_23:04:13.0");
        Order.put("size", 10);
        
        Response response=given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/placeLimitOrder")
        .then().statusCode(HttpStatus.SC_OK)
        .and().extract().response();

		assertEquals("User input size is incorrect", "ERROR: The preferred price should be a positive value greater than 0.", response.asString());
	}
	
	@Test
	public void PlaceLimitOrderInputPreviousGoodTillDate()throws JSONException{
		JSONObject Order = new JSONObject();
		Order.put("orderType", "LIMIT");
        Order.put("currencyBuy", "HKD");
        Order.put("currencySell", "USD");
        Order.put("preferredPrice", 10);
        Order.put("goodTillDate", "2017-07-30_23:04:13.0");
        Order.put("size", 10);
        
        Response response=given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/placeLimitOrder")
        .then().statusCode(HttpStatus.SC_OK)
        .and().extract().response();

		assertEquals("User input size is incorrect", "ERROR: The good till date should be of a later than current time.", response.asString());
	}
}

