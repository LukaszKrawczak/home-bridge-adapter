package com.brus5.homebridge.adapter.request;

import java.io.IOException;

public interface RequestAccessTokenHandler {
    String getAccessToken() throws IOException, InterruptedException;
}
