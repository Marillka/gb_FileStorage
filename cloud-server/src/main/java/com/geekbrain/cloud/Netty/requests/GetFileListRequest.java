package com.geekbrain.cloud.Netty.requests;

public class GetFileListRequest implements BasicRequest {

    @Override
    public String getType() {
        return "GetFileListRequest";
    }
}
