package com.google.developers.applicationApi;

public enum StatusCode {
    CODE_200(200, ""),
    CODE_401(401, "Request had invalid authentication credentials. Expected OAuth 2 access token, login cookie or other valid authentication credential."),
    CODE_403(403, "Delegation denied");

    private final int code;
    private final String msg;

    private StatusCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
