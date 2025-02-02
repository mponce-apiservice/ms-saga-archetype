package ec.com.dinersclub.test;

import static io.restassured.RestAssured.given;

import java.util.Random;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SagaResourceTest {
	
	Random rand = new Random();
	private int upperbound = 999;
	
	/*@Test
    public void testRollback() {
		int id = rand.nextInt(upperbound);
		System.out.println("new id : "+id);
        given()
                .body("{\n" + 
                		"  \"id\": "+id+",\n" + 
                		"  \"category\": {\n" + 
                		"    \"id\": 1,\n" + 
                		"    \"name\": \"newCategory\"\n" + 
                		"  },\n" + 
                		"  \"name\": \"doggie\",\n" + 
                		"  \"photoUrls\": [\n" + 
                		"    \"https://www.ecestaticos.com/image/clipping/79776773aab795837282c7d4947abaf7/por-que-nos-parece-que-los-perros-sonrien-una-historia-de-30-000-anos.jpg\"\n" + 
                		"  ],\n" + 
                		"  \"tags\": [\n" + 
                		"    {\n" + 
                		"      \"id\": 1,\n" + 
                		"      \"name\": \"newTag\"\n" + 
                		"    }\n" + 
                		"  ],\n" + 
                		"  \"status\": \"rollback\"\n" + 
                		"}")
		        .header("Content-Type", MediaType.APPLICATION_JSON)
		        .when()
		        .post("/saga/pet/create")
		        .then()
		        .statusCode(204);
    }*/

    @Test
    public void testCreate() {
    	int id = rand.nextInt(upperbound);
		System.out.println("new id : "+id);
        given()
                .body("{\n" + 
                		"  \"id\": "+id+",\n" + 
                		"  \"category\": {\n" + 
                		"    \"id\": 1,\n" + 
                		"    \"name\": \"newCategory\"\n" + 
                		"  },\n" + 
                		"  \"name\": \"doggie\",\n" + 
                		"  \"photoUrls\": [\n" + 
                		"    \"https://www.ecestaticos.com/image/clipping/79776773aab795837282c7d4947abaf7/por-que-nos-parece-que-los-perros-sonrien-una-historia-de-30-000-anos.jpg\"\n" + 
                		"  ],\n" + 
                		"  \"tags\": [\n" + 
                		"    {\n" + 
                		"      \"id\": 1,\n" + 
                		"      \"name\": \"newTag\"\n" + 
                		"    }\n" + 
                		"  ],\n" + 
                		"  \"status\": \"available\"\n" + 
                		"}")
		        .header("Content-Type", MediaType.APPLICATION_JSON)
		        .when()
		        .post("/saga/pet/create")
		        .then()
		        .statusCode(201);
    }

}