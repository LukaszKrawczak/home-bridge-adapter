package com.brus5.homebridge.adapter.security;

public interface SHAGenerator {
    String generateSHA(String timestamp, String accessToken);
}
