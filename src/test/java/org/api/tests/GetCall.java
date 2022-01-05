package org.api.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class GetCall {
    @Test
    public void singleUser() {
        Response resp = RestAssured.get("https://reqres.in/api/users/2").then().extract().response();
        Assert.assertEquals(resp.statusCode(), 200);

        JsonPath jsonresp = resp.jsonPath();
        Assert.assertEquals(jsonresp.getInt("data.id"), 2);
        Assert.assertEquals(jsonresp.getString("data.email"), "janet.weaver@reqres.in");
        Assert.assertEquals(jsonresp.getString("data.first_name"),  "Janet");
        Assert.assertEquals(jsonresp.getString("data.last_name"), "Weaver");
        Assert.assertEquals(jsonresp.getString("support.url"), "https://reqres.in/#support-heading");
        Assert.assertEquals(jsonresp.getString("support.text"),  "To keep ReqRes free, contributions towards server costs are appreciated!");
    }

    @Test
    public void allUsers(){
        Response res = RestAssured.get("https://reqres.in/api/users?page=2").then().extract().response();
        Assert.assertEquals(res.statusCode(), 200);
        JsonPath json = res.jsonPath();
        Assert.assertEquals(json.getInt("data[2].id"), 9);
        String statusLine = res.getStatusLine();
        System.out.println(res.statusLine());
        System.out.println(res.getHeader("content-type"));
        Assert.assertEquals(json.getString("data[3].email"), "byron.fields@reqres.in");


    }
    @Test
    public void singleUserNotFound(){
        Response res = RestAssured.get("https://reqres.in/api/users/23").then().extract().response();
        Assert.assertEquals(res.statusCode(), 404);
    }
    @Test
    public void listResource(){
        Response res = RestAssured.get("https://reqres.in/api/unknown").then().extract().response();
    }


}

