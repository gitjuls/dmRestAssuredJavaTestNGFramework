package com.google.developers.tests;

import com.google.developers.api.TokenManager;
import com.google.developers.applicationApi.StatusCode;
import com.google.developers.applicationApi.UserEndpoints;
import com.google.developers.api.TestBase;
import com.google.developers.applicationApi.MessageTests;
import com.google.developers.applicationApi.UserProfileTest;
import com.google.developers.pojo.*;
import com.google.developers.utils.DataLoader;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GmailApi extends TestBase implements UserEndpoints {

    @Test
    public void getUserProfile() {
        Response response = UserProfileTest.get();
        assertStatusCode(response,StatusCode.CODE_200);
        UserProfile userProfileResponse = response.as(UserProfile.class);

        assertThat(Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$")
                .matcher(userProfileResponse.getEmailAddress())
                .matches(), is(true));
    }

    @Test
    public void setInvalidEmail() {
        Response response = UserProfileTest.get(TokenManager.getToken(), "invalid.email123@gmail.com");
        assertError(response, StatusCode.CODE_403);
        assertThat(response.jsonPath().getString("error.status"), is("PERMISSION_DENIED"));
    }

    @Test
    public void setInvalidToken() {
        Response response = UserProfileTest.get("invalidToken123", DataLoader.getInstance().getUserId());
        assertError(response, StatusCode.CODE_401);
        assertThat(response.jsonPath().get("error.errors[0].message"), is("Invalid Credentials"));
        assertThat(response.jsonPath().get("error.errors[0].reason"), is("authError"));
        assertThat(response.jsonPath().get("error.status"), is("UNAUTHENTICATED"));
    }

    @Test
    public void getListTheMessagesInTheUsersMailbox() {
        Response response = MessageTests.getList();
        ListOfMessages listOfMessagesResponse = response.as(ListOfMessages.class);
        assertStatusCode(response,StatusCode.CODE_200);
        assertThat(listOfMessagesResponse.getResultSizeEstimate(), is(notNullValue()));
    }

    @DataProvider(name = "dataProvider")
    public Object[][] getData() {
        Response response = MessageTests.getList();
        ListOfMessages listOfMessagesResponse = response.as(ListOfMessages.class);

        return new Object[][]{
                {0, listOfMessagesResponse},
                {listOfMessagesResponse.getResultSizeEstimate() - 1, listOfMessagesResponse}
        };
    }

    @Test(dataProvider = "dataProvider")
    public void getTheMessage(int index, ListOfMessages listOfMessages) {
        String requestId = listOfMessages.getMessages().get(index).getId();
        String requestThreadId = listOfMessages.getMessages().get(index).getThreadId();

        Response response = MessageTests.get(requestId);
        assertStatusCode(response,StatusCode.CODE_200);
        assertThat(response.jsonPath().getList("payload.headers.value"), anyOf(hasItem("Test Email From RestAssured"), hasItem("Test Email")));

        Message messageResponse = response.as(Message.class);
        assertThat(messageResponse.getId(), equalTo(requestId));
        assertThat(messageResponse.getThreadId(), equalTo(requestThreadId));
    }

    @Test
    public void sendTheMessage() throws IOException {
        byte[] responseInBytes = Files.readAllBytes(Paths.get("src/test/resources/payloads/sendMessage.txt"));
        String msg = new String(responseInBytes);

        Response response = MessageTests.post(msg);
        assertStatusCode(response,StatusCode.CODE_200);

        Message message = response.as(Message.class);
        assertThat(message.getLabelIds().toString(), containsString("SENT"));
    }

    public void assertStatusCode(Response response, StatusCode statusCode){
        assertThat(response.getStatusCode(), equalTo(statusCode.getCode()));
    }

    public void assertError(Response response, StatusCode statusCode){
        assertThat(response.getStatusCode(), equalTo(statusCode.getCode()));
        assertThat(response.jsonPath().getString("error.message"), containsString(statusCode.getMsg()));
    }
}
