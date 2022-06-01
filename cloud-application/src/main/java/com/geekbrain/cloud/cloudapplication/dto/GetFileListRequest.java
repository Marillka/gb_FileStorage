package com.geekbrain.cloud.cloudapplication.dto;

public class GetFileListRequest implements BasicRequest {

    @Override
    public String getType() {
        return "GetFileListRequest";
    }
}
