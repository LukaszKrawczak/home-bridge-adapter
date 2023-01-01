package com.brus5.homebridge.adapter.config;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@Data
public class TuyaConfiguration {

    @Value(value = "${target.url}")
    private String targetUrl;

    @Value(value = "${client.id}")
    private String clientId;

    @Value(value = "${access.token.url}")
    private String accessTokenUrl;

    @Value(value = "${secret}")
    private String secret;

    @Value(value = "${sign.algorithm}")
    private String signAlgorithm;

    @Value(value = "${bedroom.temp.sensor}")
    private String bedroomTempSensor;

    @Value(value = "${salon.temp.sensor}")
    private String salonTempSensor;

}
