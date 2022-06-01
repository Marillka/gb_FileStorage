package com.geekbrain.cloud.Netty.requests;

public class AuthRequest implements BasicRequest {

    private String login;
    private String password;

    public AuthRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String getType() {
        return "auth";
    }
}
