package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        String BaseURL = "https://gorest.co.in/public/v2";
        String Endpoint = "/users";

        HashMap<String,String> headerKeys = new HashMap<>();
        headerKeys.put("Authorization","Bearer 4e524e66f188ae98f9eeb7eff74ffeeb64b1a016b78263a1da799e75f5cdd50d");
        // given , when , then
        // log() -> to debugge in console to give me ability to solve the issue
       Response respose = RestAssured.given().log().all()
                .headers(headerKeys)
                .contentType(ContentType.JSON)
                .body(" {\n" +
                        "        \"name\": \"Bankim Gill\",\n" +
                        "        \"email\": \"gill_bankim@enes.test\",\n" +
                        "        \"gender\": \"male\",\n" +
                        "        \"status\": \"active\"\n" +
                        "    }").post(BaseURL+Endpoint); // then (the action)

       respose.prettyPrint();
       JsonPath jsonpath  = respose.jsonPath();
        String currentId = jsonpath.get("id");
        RestAssured.given().log().all().header("Authorization","Bearer 4e524e66f188ae98f9eeb7eff74ffeeb64b1a016b78263a1da799e75f5cdd50d")
                .get(BaseURL+Endpoint+currentId).prettyPrint();
}
    }