package com.geekbrain.cloud.Netty.requests;

import java.io.Serializable;

public interface BasicRequest extends Serializable {
    String getType();
}
