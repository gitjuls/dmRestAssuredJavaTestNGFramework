package com.google.developers.api;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;

public class TestBase {

    @BeforeSuite
    public void setup(ITestContext context) throws FileNotFoundException {
        String testName = Arrays.stream(context.getAllTestMethods()).findFirst().get().getMethodName();
        String timeStamp = new Timestamp(System.currentTimeMillis()).toString().replaceAll(":", "-");
        String userDir = System.getProperty("user.dir");
        String path = userDir.concat("/src/test/resources/log/" + testName + "_" + timeStamp + ".log");
        PrintStream fileOutputStream = new PrintStream(new File(path));

        //** Creating a logging filter **//*
        RestAssured.filters(
                new RequestLoggingFilter(LogDetail.ALL, fileOutputStream),
                new ResponseLoggingFilter(LogDetail.ALL, fileOutputStream)
        );
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        System.out.println("STARTING TEST " + method.getName());
        System.out.println("THREAD ID + " + Thread.currentThread().getId());
    }
}
