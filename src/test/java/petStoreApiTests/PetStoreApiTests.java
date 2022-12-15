package petStoreApiTests;



import java.lang.reflect.Array;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetStoreApiTests {
	
	
	@Test (dependsOnMethods = "postAPet")
	public void getPetById() {
		RestAssured
		.given().accept(ContentType.JSON)
		.when().get("https://petstore.swagger.io/v2/pet/78901234")
		.then().statusCode(200);
	}
	
	
	@Test
	public void findPetByStatus() {
		RestAssured.given().accept(ContentType.JSON).contentType("application/json").param("string", "pending").when()
				.get("https://petstore.swagger.io/v2/pet/findByStatus").then().statusCode(200)
				.contentType("application/json");
	}
	
	@Test (dependsOnMethods = "postACat")
	public void getById() {

		Response myResponse = RestAssured.given().accept(ContentType.JSON).when()
				.get("https://petstore.swagger.io/v2/pet/232323");
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
				.post("https://petstore.swagger.io/v2/pet");
		
		myResponse.then().statusCode(200).and().contentType("application/json");	
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
				.post("https://petstore.swagger.io/v2/pet");
		
		myResponse.then().statusCode(200).and().contentType("application/json");
	}
}


