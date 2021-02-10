package com.epam;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class Module5_Task1
{
    @BeforeClass
    public void Module5_Task1_setup() {
        RestAssured.baseURI = "https://events.epam.com/api";
        RestAssured.basePath = "/v2";
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setContentType("application/json").
                build();
    }

    @Test
    public void verifyEvents()
    {
        String expectedEvents = "Check-in app guide";
        Response response =
                get("/events").then().statusCode(200).extract().response();

        String events = response.path("events.find {it.language == 'En'}.title");
        System.out.println(events);
        Assert.assertEquals(events, expectedEvents);
    }

}
