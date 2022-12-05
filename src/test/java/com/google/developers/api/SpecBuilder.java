package com.google.developers.api;

import com.google.developers.utils.DataLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {
    static final String uri = "https://gmail.googleapis.com";
    static final String basePath = "/gmail/v1";
    static final String accountUri = "https://oauth2.googleapis.com";

    public static RequestSpecification getRequestSpecification(){
        /** Creating an object of RequestSpecBuilder **/
        return new RequestSpecBuilder()
                .setBaseUri(uri)
                .setBasePath(basePath)
                .addHeader("Authorization", "Bearer " + TokenManager.getToken())
                .addPathParam("userId", DataLoader.getInstance().getUserId())
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getAccountRequestSpecification(){
        /** Creating an object of RequestSpecBuilder **/
        return new RequestSpecBuilder()
                .setBaseUri(accountUri)
                .setContentType(ContentType.URLENC)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getInvalidRequestSpecification(){
        /** Creating an object of RequestSpecBuilder **/
        return new RequestSpecBuilder()
                .setBaseUri(uri)
                .setBasePath(basePath)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpecification(){
        /** Creating an object of ResponseSpecBuilder **/
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

}
