package com.epam;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class Module3_Task2
{

    @BeforeClass
    public void setup()
    {
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setBaseUri("https://jsonplaceholder.typicode.com").
                setContentType("application/json").
                build();

        RestAssured.responseSpecification = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType("application/json").
                expectResponseTime(lessThan(5000l)).
                build();
    }

    // 1. Status Code = 200
    // 2. More than 3 users
    // 3. One user named "Ervin Howell"
    @Test
    public void verifyUserData()
    {
        Response response;
        response =
                when().
                        get("/users").
                then().
                        assertThat().statusCode(200).
                        body("id.size()", greaterThan(3)).
                        body("name", hasItem("Ervin Howell")).
                        extract().response();
    }
}
