package com.brus5.homebridge.adapter.security.impl;

import com.brus5.homebridge.adapter.security.SHAGenerator;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SHAGeneratorImpl implements SHAGenerator {

    @Value(value = "${sign.algorithm}")
    private String usedAlgorithm;

    @Value(value = "${client.id}")
    private String clientId;

    @Value(value = "${secret}")
    private String secret;

    public String generateSHA(String timestamp, String accessToken) {
        StringBuilder builder = new StringBuilder();
        builder.append(clientId);

        if (accessToken != null) {
            builder.append(accessToken);
        }

        builder.append(timestamp);


        return new HmacUtils(usedAlgorithm, secret).hmacHex(builder.toString()).toUpperCase();
    }
}
