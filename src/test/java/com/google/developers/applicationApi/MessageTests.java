package com.google.developers.applicationApi;

import io.restassured.http.Method;
import io.restassured.response.Response;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import static com.google.developers.api.SpecBuilder.getRequestSpecification;
import static com.google.developers.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;

public class MessageTests implements UserEndpoints {

    public static Response get(String id){
        return given().spec(getRequestSpecification())
                .pathParam("id", id)
                .config(config().logConfig(logConfig().blacklistHeader("Authorization")))
                .request(Method.GET, USER+MESSAGES+ID)
                .then().spec(getResponseSpecification())
                .extract().response();
    }

    public static Response post(String msg){
        String base64UrlEncodedMsg = new String (Base64.getUrlEncoder().encode(msg.getBytes(StandardCharsets.UTF_8)));
        HashMap<String,String> payload = new HashMap<>();
        payload.put("raw", base64UrlEncodedMsg);

        return given().spec(getRequestSpecification())
                .config(config().logConfig(logConfig().blacklistHeader("Authorization")))
                .body(payload)
                .request(Method.POST, USER+MESSAGES+SEND)
                .then().spec(getResponseSpecification())
                .extract().response();
    }

    public static Response getList(){
        return given()
                .spec(getRequestSpecification())
                .config(config().logConfig(logConfig().blacklistHeader("Authorization")))
                .request(Method.GET, USER+MESSAGES)
                .then().spec(getResponseSpecification())
                .extract().response();
    }
}
