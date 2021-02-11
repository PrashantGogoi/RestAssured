package com.epam;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Module5_Task2
{
    @BeforeClass
    public void Module5_Task2_setup()
    {
        RestAssured.baseURI = "http://api.openweathermap.org";
        RestAssured.basePath = "/data/2.5";
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setContentType("application/json").
                addParam("appid", "").
                build();
    }

    @Test
    public void verifyWeatherDetails()
    {

        // Get lat and long for city
        Response response1 =
                get("/weather?q=hyderabad").
                then().
                        statusCode(200).extract().response();
        Float cityLat = response1.path("coord.lat");
        Float cityLong = response1.path("coord.lon");

        // Query using lat and long
        Response response2 =
                get("/weather?lat=" + cityLat.toString() + "&lon=" + cityLong.toString()).
                then().
                        statusCode(200).extract().response();

        Assert.assertEquals(response2.jsonPath().get("name"), "Hyderabad");
        Assert.assertEquals(response2.jsonPath().get("sys.country"), "IN");
        Assert.assertTrue(response2.jsonPath().getFloat("main.temp_min") > 0);
        Assert.assertTrue(response2.jsonPath().getFloat("main.temp") > 0);
//                        body("main.temp_min", greaterThan(0)).
//                        body("main.temp", greaterThan(0)).

    }
}
