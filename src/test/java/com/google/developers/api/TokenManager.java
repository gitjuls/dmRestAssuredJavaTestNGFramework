package com.google.developers.api;

import com.google.developers.utils.ConfigLoader;
import com.google.developers.applicationApi.UserEndpoints;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.time.Instant;
import java.util.HashMap;
import static com.google.developers.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;

public class TokenManager implements UserEndpoints {

    private static String token;
    private static Instant expiryTime;

    public synchronized static String getToken(){
        try{
            if(token == null || Instant.now().isAfter(expiryTime)){
                System.out.println("Renewing token");
                Response response = renewToken();
                token = response.jsonPath().getString("access_token");
                int expiryDurationInSeconds = response.jsonPath().get("expires_in");
                expiryTime = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            }else{
                System.out.println("token is good to use");
            }

        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to get token");
        }
        return token;
    }

    private static Response renewToken(){
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("client_id", ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());

        Response response = given().spec(SpecBuilder.getAccountRequestSpecification())
                .formParams(formParams)
                .request(Method.POST, TOKEN)
                .then().spec(getResponseSpecification())
                .extract().response();

        if(response.getStatusCode() != 200){
            throw new RuntimeException("Renew Token");
        }
        return response;
    }
}
