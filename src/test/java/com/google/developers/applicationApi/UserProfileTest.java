package com.google.developers.applicationApi;

import io.restassured.http.Method;
import io.restassured.response.Response;

import static com.google.developers.api.SpecBuilder.*;
import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;

public class UserProfileTest implements UserEndpoints {

    public static Response get(){
        return given().spec(getRequestSpecification())
                .config(config().logConfig(logConfig().blacklistHeader("Authorization")))
                .request(Method.GET, USER+PROFILE)
                .then().spec(getResponseSpecification())
                .extract().response();
    }

    public static Response get(String token, String userId){
        return given().spec(getInvalidRequestSpecification())
                .auth().oauth2(token)
                .pathParam("userId", userId)
                .config(config().logConfig(logConfig().blacklistHeader("Authorization")))
                .request(Method.GET, USER+PROFILE)
                .then().spec(getResponseSpecification())
                .extract().response();
    }
}
