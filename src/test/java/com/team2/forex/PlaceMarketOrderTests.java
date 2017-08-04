package com.team2.forex;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
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
import com.team2.forex.entity.HistoricalTradeData;
import com.team2.forex.repository.MarketOrderRepository;
import com.team2.forex.service.ForexStreamEmulationService;
import com.team2.forex.service.MarketOrderService;

import io.restassured.RestAssured;
import io.restassured.response.Response;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT, classes = { ForexApplication.class})
public class PlaceMarketOrderTests {

	@Autowired
	private MarketOrderService MktOrderSev;

	@Autowired
	private MarketOrderRepository MktOrderRepo;
	
	@Autowired
	private ForexStreamEmulationService emulationService;
	
	
	@Value("${local.server.port}")
	private int serverPort;
	
	@Before
	public void init(){
		 RestAssured.port = serverPort;
	}
	
	
	@Test
    public void PlaceMarketOrderSuccess() throws JSONException, ParseException {
		//generate historical trade data
		String streamJson = emulationService.generateStreamJson();
		List<HistoricalTradeData> historicalTradeDataList = emulationService.parseStreamJson(streamJson);
		emulationService.saveHistoricalTradeData(historicalTradeDataList);
		
		//place market order
		JSONObject Order = new JSONObject();
		Order.put("orderType", "MARKET");
        Order.put("currencyBuy", "HKD");
        Order.put("currencySell", "USD");
        Order.put("size", 120);

        given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/placeMarketOrder").then()
        .statusCode(200);
    }
	
	@Test
	public void PlaceMarketOrderInputWrongOrderType()throws JSONException{
		JSONObject Order = new JSONObject();
		Order.put("orderType", "random");
        Order.put("currencyBuy", "HKD");
        Order.put("currencySell", "USD");
        Order.put("size", 120);
        
        Response response=given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/placeMarketOrder")
        .then().statusCode(HttpStatus.SC_OK)
        .and().extract().response();

		assertEquals("User input Order Type is incorrect", "ERROR: OrderType should only be Market.", response.asString());
	}
	
	@Test
	public void PlaceMarketOrderInputWrongBuyCurrency()throws JSONException{
		JSONObject Order = new JSONObject();
		Order.put("orderType", "MARKET");
        Order.put("currencyBuy", "INR");
        Order.put("currencySell", "USD");
        Order.put("size", 120);
        
        Response response=given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/placeMarketOrder")
        .then().statusCode(HttpStatus.SC_OK)
        .and().extract().response();

		assertEquals("User input Buy Currency is not supported", "ERROR: Buy Currency not supported.", response.asString());
	}
    
	@Test
	public void PlaceMarketOrderInputWrongSellCurrency()throws JSONException{
		JSONObject Order = new JSONObject();
		Order.put("orderType", "MARKET");
        Order.put("currencyBuy", "SGD");
        Order.put("currencySell", "INR");
        Order.put("size", 120);
        
        Response response=given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/placeMarketOrder")
        .then().statusCode(HttpStatus.SC_OK)
        .and().extract().response();

		assertEquals("User input Sell Currency is not supported", "ERROR: Sell Currency not supported.", response.asString());
	}
	
	@Test
	public void CancelOrderCorrectly()throws JSONException{
		JSONObject Order = new JSONObject();
		Order.put("orderNumber", "c81b59cb37dbe7d2607432877c12cac2");
        
		given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/cancelLimitOrder").then()
        .statusCode(200);
    }
	
	@Test
	public void CancelOrderWithWrongOrderNumber()throws JSONException{
		JSONObject Order = new JSONObject();
		Order.put("orderNumber", "abcdefg");
        
        Response response=given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/cancelLimitOrder")
        .then().statusCode(HttpStatus.SC_OK)
        .and().extract().response();

		assertEquals("User cancel with wrong order number", "ERROR: The order number is incorret.", response.asString());
	}
	
	@Test
	public void CancelOrderWithWrongUser()throws JSONException{
		JSONObject Order = new JSONObject();
		Order.put("orderNumber", "c81b587640dbe7d2607432877c234ac2");
        
        Response response=given()
        .auth().basic("admin", "admin")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/cancelLimitOrder")
        .then().statusCode(HttpStatus.SC_OK)
        .and().extract().response();

		assertEquals("User cancel with wrong user", "ERROR: The login ID mismatch with order owner, please try again.", response.asString());
	}
	
	@Test
	public void PlaceMarketOrderInputWrongSize()throws JSONException{
		JSONObject Order = new JSONObject();
		Order.put("orderType", "MARKET");
        Order.put("currencyBuy", "SGD");
        Order.put("currencySell", "USD");
        Order.put("size", 0);
        
        Response response=given()
        .auth().basic("client", "client")
        .contentType("application/json")
        .body(Order.toString())
        .when().post("/placeMarketOrder")
        .then().statusCode(HttpStatus.SC_OK)
        .and().extract().response();

		assertEquals("User input size is incorrect", "ERROR: The size should be a positive value greater than 0.", response.asString());
	}
	
}

