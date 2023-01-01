package com.brus5.homebridge.adapter.request.impl;

import com.brus5.homebridge.adapter.security.SHAGenerator;
import com.brus5.homebridge.adapter.config.TuyaConfiguration;
import com.brus5.homebridge.adapter.model.device.DeviceResponseDto;
import com.brus5.homebridge.adapter.request.RequestAccessTokenHandler;
import com.brus5.homebridge.adapter.request.RequestTempSensorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RequestHandlerImpl implements RequestAccessTokenHandler, RequestTempSensorHandler {

    private static final String CLIENT_ID = "client_id";
    private static final String SIGN = "sign";
    private static final String T = "t";
    private static final String SIGN_METHOD = "sign_method";

    private TuyaConfiguration tuyaConfiguration;
    private ObjectMapper objectMapper;
    private SHAGenerator shaGenerator;

    @Override
    public String getAccessToken() throws IOException, InterruptedException {
        final String timestamp = String.valueOf(System.currentTimeMillis());
        final String easySign = shaGenerator.generateSHA(timestamp, null);
        final HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(tuyaConfiguration.getAccessTokenUrl()))
                .header(CLIENT_ID, tuyaConfiguration.getClientId())
                .header(SIGN, easySign)
                .header(T, timestamp)
                .header(SIGN_METHOD, "HMAC-SHA256")
                .GET()
                .build();

        HttpResponse<String> httpResponse = HttpClient.newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return httpResponse.body();
    }

    @Override
    public DeviceResponseDto getTempSensor(String accessToken, String deviceId) throws IOException, InterruptedException {
        final String timestamp = String.valueOf(System.currentTimeMillis());
        final String easySign = shaGenerator.generateSHA(timestamp, accessToken);
        final HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(tuyaConfiguration.getTargetUrl() + "/v1.0/devices/" + deviceId))
                .header(CLIENT_ID, tuyaConfiguration.getClientId())
                .headers("access_token", accessToken)
                .header(SIGN, easySign)
                .header(T, timestamp)
                .header(SIGN_METHOD, "HMAC-SHA256")
                .GET()
                .build();
        HttpResponse<String> httpResponse = HttpClient.newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(httpResponse.body(), DeviceResponseDto.class);
    }
}
