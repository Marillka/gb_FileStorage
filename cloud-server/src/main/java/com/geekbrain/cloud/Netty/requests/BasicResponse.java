package com.geekbrain.cloud.Netty.requests;

import java.io.Serializable;

public class BasicResponse implements Serializable {
    private String response;

    public BasicResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
