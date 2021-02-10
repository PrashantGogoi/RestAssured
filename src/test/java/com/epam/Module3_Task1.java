package com.epam;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class Module3_Task1
{

    @BeforeClass
    public void setup()
    {
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setBaseUri("https://petstore.swagger.io/v2").
                setContentType("application/json").
                build();

        RestAssured.responseSpecification = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType("application/json").
                expectResponseTime(lessThan(5000l)).
                build();

    }

    @Test
    public void createPet()
    {
        String inputJSON = "{\n" +
                "\t\"id\": 12345,\n" +
                "\t\"category\": {\n" +
                "\t\t\"id\": 1,\n" +
                "\t\t\"name\": \"dog\"\n" +
                "\t},\n" +
                "\t\"name\": \"snoopie\",\n" +
                "\t\"photoUrls\": [\n" +
                "\t\t\"string\"\n" +
                "\t],\n" +
                "\t\"tags\": [{\n" +
                "\t\t\"id\": 0,\n" +
                "\t\t\"name\": \"string\"\n" +
                "\t}],\n" +
                "\t\"status\": \"pending\"\n" +
                "}";

        given().
            body(inputJSON).
        when().
            post("/pet").
        then().
            assertThat().statusCode(200);

        System.out.println(inputJSON);
    }

    // 1. Status Code is 200 - checked in Setup function
    // 2. Content-Type is application/json - checked in Setup function
    // 3. Pet is a dog
    // 4. Name is "snoopie"
    // 5. Status is pending
    @Test
    public void verifyPetInfo()
    {
        when().
            get("/pet/12345").
        then().
            assertThat().
            body("category.name", equalTo("dog")).
            body("name", equalTo("snoopie")).
            body("status", equalTo("pending"));
    }
}
