package petStoreApiTests;



import java.io.File;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import groovyjarjarantlr4.v4.runtime.atn.SemanticContext.AND;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetStoreApiTests {
	
	
	int catID, petID, donkeyID;
	
	@BeforeTest
	public void setup() {
		RestAssured.baseURI="https://petstore.swagger.io/v2";
	}
	
	
	@Test (dependsOnMethods = "postAPet")
	public void getPetById() {
		RestAssured
		.given().accept(ContentType.JSON)
		.when().get("/pet/78901234")
		.then().statusCode(200);
	}
	
	
	@Test
	public void findPetByStatus() {
		RestAssured.given().accept(ContentType.JSON).contentType("application/json").param("string", "pending").when()
				.get("/pet/findByStatus").then().statusCode(200)
				.contentType("application/json");
	}
	
	
	@Test (dependsOnMethods = {"postACat", "updateCat"})
	public void getById() {

		Response myResponse = RestAssured.given().accept(ContentType.JSON).when()
				.get("/pet/232323");
		myResponse.prettyPrint();
		// Verifying the status code
		myResponse.then().assertThat().statusCode(200).and().contentType("application/json");
		
		String petName = myResponse.path("name");
		System.out.println("Pet name is: " + petName);
		Assert.assertEquals(petName, "Ember");
		
		int id = myResponse.body().path("id");
		System.out.println("ID is: " + id);
		Assert.assertEquals(id, 232323);
		
		int tagsId = myResponse.path("tags[0].id");
		System.out.println("Tag name is: " + tagsId);
		Assert.assertEquals(tagsId, 18);
		
		String tags2Name = myResponse.path("tags[1].name");
		System.out.println("Pet tag second name is: " + tags2Name);
		Assert.assertEquals(tags2Name, "Anatolian");
		
		//using jsonpath function
		String name2 = myResponse.jsonPath().get("category.name");
		System.out.println("Second name: " + name2);
		Assert.assertEquals(name2, "cat");
		
		String catStatus = myResponse.body().jsonPath().get("status");
		Assert.assertEquals(catStatus, "pending");
	}
	
	
	@Test	
	public void postACat() {
		
		String catRequestBody = "{\r\n"
				+ "  \"id\": 789012345,\r\n"
				+ "  \"category\": {\r\n"
				+ "    \"id\": 555,\r\n"
				+ "    \"name\": \"feline\"\r\n"
				+ "  },\r\n"
				+ "  \"name\": \"kitty\",\r\n"
				+ "  \"photoUrls\": [\r\n"
				+ "    \"string\"\r\n"
				+ "  ],\r\n"
				+ "  \"tags\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 0,\r\n"
				+ "      \"name\": \"CAT\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"available\"\r\n"
				+ "}";
		
		Response myResponse = RestAssured.given().accept(ContentType.JSON).contentType("application/json").body(catRequestBody).when()
				.post("/pet");
		
		myResponse.then().statusCode(200).and().contentType("application/json");		
		catID = myResponse.jsonPath().get("id");
	}
	
	
	
	
	//update the cat status to pending
	@Test  (dependsOnMethods = "postACat")
	public void updateCat() {
		String updateRequestBody="{\r\n"
				+ "  \"id\": 789012345,\r\n"
				+ "  \"category\": {\r\n"
				+ "    \"id\": 555,\r\n"
				+ "    \"name\": \"feline\"\r\n"
				+ "  },\r\n"
				+ "  \"name\": \"kitty\",\r\n"
				+ "  \"photoUrls\": [\r\n"
				+ "    \"string\"\r\n"
				+ "  ],\r\n"
				+ "  \"tags\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 0,\r\n"
				+ "      \"name\": \"CAT\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"pending\"\r\n"
				+ "}";
		
		Response catUpdateResponse = RestAssured.given().accept(ContentType.JSON).contentType("application/json")
		.body(updateRequestBody).when().put("/pet");
		
		catUpdateResponse.then().statusCode(200).and().contentType("application/json");
		Assert.assertEquals(catUpdateResponse.body().jsonPath().get("status"),"pending");	
		
	}
	
	
	
	
	
	
	//Attempt to update the cat status using invalid ID
	@Test 
	public void invalidUpdateCat() {
		String updateRequestBody="{\r\n"
				+ "  \"id\": '789012345',\r\n"
				+ "  \"category\": {\r\n"
				+ "    \"id\": 555,\r\n"
				+ "    \"name\": \"feline\"\r\n"
				+ "  },\r\n"
				+ "  \"name\": \"kitty\",\r\n"
				+ "  \"photoUrls\": [\r\n"
				+ "    \"string\"\r\n"
				+ "  ],\r\n"
				+ "  \"tags\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 0,\r\n"
				+ "      \"name\": \"CAT\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"pending\"\r\n"
				+ "}";
		
		Response catUpdateResponse = RestAssured.given().accept(ContentType.JSON).contentType("application/json")
		.body(updateRequestBody).when().put("/pet");
				
		catUpdateResponse.then().statusCode(400).and().contentType("application/json");
		Assert.assertEquals(catUpdateResponse.body().jsonPath().get("message"),"bad input");	
	}
	

	
	
	
	@Test
	public void postAPet() {
		
		String dogRequestBody = "{\r\n"
				+ "  \"id\": 78901234,\r\n"
				+ "  \"category\": {\r\n"
				+ "    \"id\": 89,\r\n"
				+ "    \"name\": \"canine\"\r\n"
				+ "  },\r\n"
				+ "  \"name\": \"doggie\",\r\n"
				+ "  \"photoUrls\": [\r\n"
				+ "    \"string\"\r\n"
				+ "  ],\r\n"
				+ "  \"tags\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 80,\r\n"
				+ "      \"name\": \"Kangal\"\r\n"
				+ "    }, \r\n"
				+ "    {\r\n"
				+ "      \"id\": 81,\r\n"
				+ "      \"name\": \"German Shepherd\"\r\n"
				+ "    },\r\n"
				+ "     {\r\n"
				+ "      \"id\": 82,\r\n"
				+ "      \"name\": \"Husky\"\r\n"
				+ "    }\r\n"
				+ "\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"available\"\r\n"
				+ "}";
		
		Response myResponse = RestAssured.given().accept(ContentType.JSON).contentType("application/json").body(dogRequestBody).when()
				.post("/v2/pet");	
		
		myResponse.then().statusCode(200).and().contentType("application/json");		
		petID = myResponse.jsonPath().get("id");
	}
	
	
	
	
	//create a pet with request body in json file	
	@Test
	public void createDonkeyWithJsonFile() {
		File donkeyRequestBodyFile = new File("./src/test/resources/jsonTestData/createPet.json");
				
		Response myResponse = RestAssured.given().accept(ContentType.JSON).contentType("application/json").body(donkeyRequestBodyFile).when()
		.post("/pet");	
		
		myResponse.then().statusCode(200).and().contentType("application/json");		
		donkeyID = myResponse.jsonPath().get("id");
	}
	
	
	
	
	//RestAssured chain validation
	@Test
	public void chainValidation() {
		File requestBodyFile = new File("./src/test/resources/jsonTestData/createPet.json");
		
		Response myResponse = RestAssured.given().accept(ContentType.JSON).contentType("application/json").body(requestBodyFile).when()
		.post("/pet");
		
		myResponse
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType("application/json")
		.and().assertThat().body("id", Matchers.equalTo(7890))
		.and().assertThat().body("category.id", Matchers.equalTo(33))
		.and().assertThat().body("category.name",Matchers.equalTo("donkey"))
		.and().assertThat().body("name", Matchers.equalTo("Stubborn Donkey"))
		.and().assertThat().body("tags[41].id",Matchers.equalTo(41))
		.and().assertThat().body("tags[41].name",Matchers.equalTo("Donkey41"))
		.and().assertThat().body("tags[42].id",Matchers.equalTo(42))
		.and().assertThat().body("tags[42].name",Matchers.equalTo("Donkey42"))
		.and().assertThat().body("tags[43].id",Matchers.equalTo(43))
		.and().assertThat().body("tags[43].name",Matchers.equalTo("Donkey43"))
		.and().assertThat().body("status",Matchers.equalTo("available"));
		
		myResponse.prettyPrint();
		
		donkeyID = myResponse.jsonPath().getInt("id");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@AfterTest 
	public void cleanup() {
		//deleteCat();
		//deletePet();
		//deleteDonkey();
	}
	
	
	public void deletePet() {	
		Response deleteResponse = RestAssured.given().accept(ContentType.JSON).contentType("application/json")
		.delete("/pet/"+petID);  //must provide full URL including the petID 	
		deleteResponse.then().statusCode(200).and().contentType("application/json");
		Assert.assertEquals(deleteResponse.body().jsonPath().get("message"), ""+petID);	
	}
	
	
	
	public void deleteCat() {	
		Response deleteResponse = RestAssured.given().accept(ContentType.JSON).contentType("application/json")
		.delete("/pet/"+catID);  //must provide full URL including the petID 	
		deleteResponse.then().statusCode(200).and().contentType("application/json");
		Assert.assertEquals(deleteResponse.body().jsonPath().get("message"), ""+catID);	
	}
	
	
	public void deleteDonkey() {	
		Response deleteResponse = RestAssured.given().accept(ContentType.JSON).contentType("application/json")
		.delete("/pet/"+donkeyID);  //must provide full URL including the petID 	
		deleteResponse.then().statusCode(200).and().contentType("application/json");
		Assert.assertEquals(deleteResponse.body().jsonPath().get("message"), ""+donkeyID);	
	}
}


