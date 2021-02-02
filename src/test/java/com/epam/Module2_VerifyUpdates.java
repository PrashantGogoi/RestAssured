package com.epam;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.junit.Test;


public class Module2_VerifyUpdates
{

    @Test
    public void addResource()
    {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        JSONObject requestBody = new JSONObject();
        requestBody.put("userId", 1);
        requestBody.put("title", "No Latin");
        requestBody.put("body", "Me no understand Latino");
        Response response;
        response =
            given().
                header("Content-type", "application/json").and().
                body(requestBody.toString()).
            when().
                post("/posts").
            then().
                assertThat().statusCode(201).
                extract().response();

        Assert.assertEquals(response.jsonPath().getString("userId"), "1");
        Assert.assertEquals(response.jsonPath().getString("title"), "No Latin");
        Assert.assertEquals(response.jsonPath().getString("body"), "Me no understand Latino");
    }

    @Test
    public void updateResource()
    {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", 1);
        requestBody.put("userId", 1);
        requestBody.put("title", "Title corrected");
        requestBody.put("body", "Body corrected");
        Response response;
        response =
                given().
                        header("Content-type", "application/json").and().
                        body(requestBody.toString()).
                        when().
                        put("/posts/1").
                        then().
                        assertThat().statusCode(200).
                        extract().response();

        Assert.assertEquals(response.jsonPath().getString("id"), "1");
        Assert.assertEquals(response.jsonPath().getString("userId"), "1");
        Assert.assertEquals(response.jsonPath().getString("title"), "Title corrected");
        Assert.assertEquals(response.jsonPath().getString("body"), "Body corrected");
    }

    @Test
    public void deleteResource()
    {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        Response response;
        response =
                given().
                        header("Content-type", "application/json").and().
                        when().
                        delete("/posts/1").
                        then().
                        assertThat().statusCode(200).
                        extract().response();

        Assert.assertEquals(response.jsonPath().getString("id"), null);
        Assert.assertEquals(response.jsonPath().getString("userId"), null);
        Assert.assertEquals(response.jsonPath().getString("title"), null);
        Assert.assertEquals(response.jsonPath().getString("body"), null);
    }
}
