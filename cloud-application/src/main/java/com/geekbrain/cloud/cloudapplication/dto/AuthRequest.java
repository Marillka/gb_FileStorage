package com.geekbrain.cloud.cloudapplication.dto;

public class AuthRequest implements BasicRequest {

    private String login;
    private String password;

    public AuthRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String getType() {
        return "auth";
    }
}
