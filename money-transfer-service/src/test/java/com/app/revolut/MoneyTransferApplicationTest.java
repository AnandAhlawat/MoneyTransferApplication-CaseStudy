package com.app.revolut;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.app.revolut.config.RevolutConfig;
import com.google.inject.Guice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class MoneyTransferApplicationTest    
{
	private static final int TEST_PORT=9998;

	@BeforeAll
	public static void initialize()
	{

		Guice.createInjector(new RevolutConfig())
		.getInstance(MoneyTransferApplication.class)
		.run(TEST_PORT);

		RestAssured.baseURI = "http://localhost";
		RestAssured.port = TEST_PORT;
	}

	@Test
	@Order(1)    
	public void addBeneficiaryPositive() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n" + 
				"    \"authenticatedAccountNumber\": 92501,\r\n" + 
				"    \"beneficiaryDetails\": {\r\n" + 
				"        \"accountNumber\": 92502,\r\n" + 
				"        \"beneficiaryName\": \"Monika\",\r\n" + 
				"        \"bankCode\": \"HSBC01342345\"\r\n" + 
				"    }\r\n" + 
				"}").when().post("/beneficiaries");
		System.out.println(response.asString());
		response.then().body("accountNumber", Matchers.is(92502));
		Assertions.assertEquals(response.getStatusCode(),200);
	}

	@Test
	@Order(2)    
	public void verifyBeneficiaryPositive() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n" + 
				"    \"authenticatedAccountNumber\": \"92501\",\r\n" + 
				"    \"beneficiaryAccountNumber\": \"92502\"\r\n" + 
				"}").when().put("/beneficiaries/verify");
		System.out.println(response.asString());
		response.then().body("beneficiaryAccountNumber", Matchers.is(92502));
		response.then().body("rmtErrors", Matchers.hasSize(0)); 

		Assertions.assertEquals(response.getStatusCode(),200);
	}

	@Test
	@Order(3)    
	public void updateTransferLimitPositive() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n" + 
				"    \"authenticatedAccountNumber\": 92501,\r\n" + 
				"    \"benefeciaryAccountNumber\": 92502,\r\n" + 
				"    \"transferLimit\": {\r\n" + 
				"        \"dailyLimit\": \"500.00\",\r\n" + 
				"        \"maxNoOfTransactions\": 3\r\n" + 
				"    }\r\n" + 
				"}").when().put("/beneficiaries/UpdateTransferlimit");
		System.out.println(response.asString());
		response.then().body("transferLimit.maxNoOfTransactions", Matchers.is(3));
		response.then().body("transferLimit.dailyLimit", Matchers.is(500f));
		response.then().body("rmtErrors", Matchers.hasSize(0)); 
		//Assertions.assertEquals(response.getBody().path("transferLimit.dailyLimit"), 1500.00);
		Assertions.assertEquals(response.getStatusCode(),200);	
	}

	@Test
	@Order(4)    
	public void getAccountBalancePositive() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n" + 
				"    \"authenticatedAccountNumber\": \"92501\"\r\n" + 
				"}").when().post("/accounts/checkBalance");
		System.out.println(response.asString());
		response.then().body("accountNumber", Matchers.is(92501));
		response.then().body("amount", Matchers.is(1000));
		response.then().body("rmtErrors", Matchers.hasSize(0));
		Assertions.assertEquals(response.getStatusCode(),200);
	}
	
	
	@Test
	@Order(5)    
	public void transferMoney1Positive() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n" + 
				"    \"authenticatedAccountNumber\": 92501,\r\n" + 
				"    \"beneficiaryAccountNumber\": 92502,\r\n" + 
				"    \"amount\": 100,\r\n" + 
				"    \"description\": \"Free Money \"\r\n" + 
				"}").when().post("/accounts/transfer");
		System.out.println(response.asString());
		response.then().body("status", Matchers.is("success"));
		response.then().body("amount", Matchers.is(100));
		response.then().body("rmtErrors", Matchers.hasSize(0));
		Assertions.assertEquals(response.getStatusCode(),200);
	}

	
	@Test
	@Order(6)    
	public void getAccountBalanceAfterDeduction1Positive() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n" + 
				"    \"authenticatedAccountNumber\": \"92501\"\r\n" + 
				"}").when().post("/accounts/checkBalance");
		System.out.println(response.asString());
		response.then().body("accountNumber", Matchers.is(92501));
		response.then().body("amount", Matchers.is(900));
		response.then().body("rmtErrors", Matchers.hasSize(0));
		Assertions.assertEquals(response.getStatusCode(),200);
	}
	
	@Test
	@Order(6)    
	public void getBeneficryAccountBalanceAfterDeduction1Positive() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n" + 
				"    \"authenticatedAccountNumber\": \"92502\"\r\n" + 
				"}").when().post("/accounts/checkBalance");
		System.out.println(response.asString());
		response.then().body("accountNumber", Matchers.is(92502));
		response.then().body("amount", Matchers.is(1100));
		response.then().body("rmtErrors", Matchers.hasSize(0));
		Assertions.assertEquals(response.getStatusCode(),200);
	}
	
	
	@Test
	@Order(7)    
	public void transferMoney2Positive() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n" + 
				"    \"authenticatedAccountNumber\": 92501,\r\n" + 
				"    \"beneficiaryAccountNumber\": 92502,\r\n" + 
				"    \"amount\": 200,\r\n" + 
				"    \"description\": \"Free Money \"\r\n" + 
				"}").when().post("/accounts/transfer");
		System.out.println(response.asString());
		response.then().body("status", Matchers.is("success"));
		response.then().body("amount", Matchers.is(200));
		response.then().body("rmtErrors", Matchers.hasSize(0));
		Assertions.assertEquals(response.getStatusCode(),200);
	}
	
	@Test
	@Order(8)    
	public void getAccountBalanceAfterDeduction2Positive() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n" + 
				"    \"authenticatedAccountNumber\": \"92501\"\r\n" + 
				"}").when().post("/accounts/checkBalance");
		System.out.println(response.asString());
		response.then().body("accountNumber", Matchers.is(92501));
		response.then().body("amount", Matchers.is(700));
		response.then().body("rmtErrors", Matchers.hasSize(0));
		Assertions.assertEquals(response.getStatusCode(),200);
	}
	

	@Test
	@Order(9)    
	public void getBeneficryAccountBalanceAfterDeduction2Positive() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n" + 
				"    \"authenticatedAccountNumber\": \"92502\"\r\n" + 
				"}").when().post("/accounts/checkBalance");
		System.out.println(response.asString());
		response.then().body("accountNumber", Matchers.is(92502));
		response.then().body("amount", Matchers.is(1300));
		response.then().body("rmtErrors", Matchers.hasSize(0));
		Assertions.assertEquals(response.getStatusCode(),200);
	}
	
	
	@Test
	@Order(10)    
	public void transferMoney3Negative() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n" + 
				"    \"authenticatedAccountNumber\": 92501,\r\n" + 
				"    \"beneficiaryAccountNumber\": 92502,\r\n" + 
				"    \"amount\": 201,\r\n" + 
				"    \"description\": \"Free Money \"\r\n" + 
				"}").when().post("/accounts/transfer");
		System.out.println(response.asString());
		response.then().body("errorCode", Matchers.hasItem(108));
		response.then().body("errorMessage", Matchers.hasItem("Maximum amount per day allowed for the benefeciary have been exceeded"));		
		Assertions.assertEquals(response.getStatusCode(),400);
	}
	
	@Test
	@Order(11)    
	public void transferMoney4Positive() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n" + 
				"    \"authenticatedAccountNumber\": 92501,\r\n" + 
				"    \"beneficiaryAccountNumber\": 92502,\r\n" + 
				"    \"amount\": 200,\r\n" + 
				"    \"description\": \"Free Money \"\r\n" + 
				"}").when().post("/accounts/transfer");
		System.out.println(response.asString());
		response.then().body("status", Matchers.is("success"));
		response.then().body("amount", Matchers.is(200));
		response.then().body("rmtErrors", Matchers.hasSize(0));
		Assertions.assertEquals(response.getStatusCode(),200);
	}
	
	@Test
	@Order(12)    
	public void transferMoney4Negative() {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body("{\r\n" + 
				"    \"authenticatedAccountNumber\": 92501,\r\n" + 
				"    \"beneficiaryAccountNumber\": 92502,\r\n" + 
				"    \"amount\": 600,\r\n" + 
				"    \"description\": \"Free Money \"\r\n" + 
				"}").when().post("/accounts/transfer");
		System.out.println(response.asString());
		response.then().body("errorCode", Matchers.hasItem(106));
		response.then().body("errorMessage", Matchers.hasItem("Authenticated account doesn't have the required funds to do this transaction, Please try with a lower amount"));		
		Assertions.assertEquals(response.getStatusCode(),400);
	}




}
