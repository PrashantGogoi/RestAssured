package com.epam;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class Module4_Task1
{
    private static int empCount = 0;
    private static String empId = "";

    @BeforeClass
    public static void Module4_Task1_setup()
    {
        RestAssured.baseURI = "http://dummy.restapiexample.com/api";
        RestAssured.basePath = "/v1";
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setContentType("application/json").
                build();
    }

    // 1. Get all employees
    // 2. Verify employee count
    @Test
    public static void getAllEmployees()
    {
        setEmployeeCount();
        get("/employees").
        then().
                statusCode(200).
                body("data.id.size()", equalTo(empCount));
    }

    // 1. Create employee
    // 2. Verify employee created successfully
    // 3. Verify employee count increased by one
    @Test
    public static void createAndVerifyEmployee()
    {
        Map<String, Object> employeeMap = new HashMap<>();
        employeeMap = getEmployeeMap("Jason", 20000, 32);

        setEmployeeCount();
        // Create new employee
        empId =
                given().
                        body(employeeMap).
                when().
                        post("/create").
                then().
                        statusCode(200).
                        body("status", equalTo("success")).
                        extract().path("data.id").toString();
        System.out.println("New employee created with id: " + empId);

        // Verify employee count increased by 1
        empCount = empCount + 1;
        get("/employees").
        then().
                statusCode(200).
                body("data.id.size()", equalTo(empCount));

        // Verify new employee info is correct
        get("/employee/" + empId).
        then().
                statusCode(200).
                body("data.name", equalTo(employeeMap.get("name"))).
                body("data.salary", equalTo(employeeMap.get("salary"))).
                body("data.age", equalTo(employeeMap.get("salary")));
        System.out.println("Verified employee details for: " + empId);
    }

    @Test
    public static void updateAndVerifyEmployee()
    {
        int salaryToUpdate = 50000;
        int ageToUpdate = 40;

        // Get employee details as map
        Map<String, Object> employeeMap = new HashMap<>();
        employeeMap = getEmployeeDetailsMap(empId);
        employeeMap.put("id", empId);
        employeeMap.put("salary", salaryToUpdate);
        employeeMap.put("age", ageToUpdate);

        given().
                body(employeeMap).
        when().
                put("/update/" + empId).
        then().
                statusCode(200);

        // Get employee details again and verify updated fields
        employeeMap = getEmployeeDetailsMap(empId);
        Assert.assertEquals(employeeMap.get("salary"), salaryToUpdate);
        Assert.assertEquals(employeeMap.get("age"), ageToUpdate);
    }

    @Test
    public static void deleteAndVerifyEmployee()
    {
        empId = "4291";
        setEmployeeCount();
        // Delete empId created
        when().
                delete("/delete/" + empId).
        then().
                statusCode(200).
                body("status", equalTo("success"));

        // Verify by Get
        get("/employee/" + empId).
        then().
                statusCode(404).
                body("status", equalTo("success"));
        System.out.println("Employee: " + empId + " successfully deleted");

        // Verify employee count decreased by 1
        empCount = empCount - 1;
        get("/employees").
        then().
                statusCode(200).
                body("data.id.size()", equalTo(empCount));
        System.out.println("Employee count after deletion: " + empCount);
    }

    public static void setEmployeeCount()
    {
        empCount = get("/employees").then().extract().path("data.id.size()");
        System.out.println("Total employees: " + empCount);
    }

    public static Map getEmployeeMap(String name, int salary, int age)
    {
        Map<String, Object> employeeMap = new HashMap<>();
        employeeMap.put("name", name);
        employeeMap.put("salary", salary);
        employeeMap.put("age", age);
        return employeeMap;
    }

    public static Map getEmployeeDetailsMap(String empId)
    {
        Map<String, Object> employeeMap = new HashMap<>();
        Response response =
                get("/employee" + empId).
                then().
                        statusCode(200).
                        extract().response();
        employeeMap.put("name", response.jsonPath().getString("name"));
        employeeMap.put("salary", response.jsonPath().getString("salary"));
        employeeMap.put("age", response.jsonPath().getString("age"));
        return employeeMap;
    }
}
