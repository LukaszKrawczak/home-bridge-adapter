package com.brus5.homebridge.adapter;

import com.brus5.homebridge.adapter.config.TuyaConfiguration;
import com.brus5.homebridge.adapter.helper.StringHelper;
import com.brus5.homebridge.adapter.model.TempSensorModel;
import com.brus5.homebridge.adapter.model.access.token.AccessTokenResponseDto;
import com.brus5.homebridge.adapter.model.device.DeviceResponseDto;
import com.brus5.homebridge.adapter.request.RequestAccessTokenHandler;
import com.brus5.homebridge.adapter.request.RequestTempSensorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@AllArgsConstructor
public class WebController {

    private RequestAccessTokenHandler requestAccessTokenHandler;
    private RequestTempSensorHandler requestTempSensorHandler;
    private TuyaConfiguration tuyaConfiguration;
    private StringHelper stringHelper;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/access-token")
    public @ResponseBody ResponseEntity<String> accessToken() throws IOException, InterruptedException {
        final String accessToken = requestAccessTokenHandler.getAccessToken();
        log.info("Generated access token {}", accessToken);
        return ResponseEntity.ok(accessToken);
    }

    @RequestMapping(value = "/temp-sensor/1", produces = MediaType.APPLICATION_JSON_VALUE)
    public String tempSensorSalon() throws IOException, InterruptedException {
        final AccessTokenResponseDto accessTokenResult = objectMapper.readValue(accessToken().getBody(), AccessTokenResponseDto.class);
        final DeviceResponseDto response = requestTempSensorHandler.getTempSensor(accessTokenResult.getAccessTokenResultDto().getAccessToken(), tuyaConfiguration.getSalonTempSensor());
        TempSensorModel tempSensorModel = new TempSensorModel();
        tempSensorModel.setTemperature(stringHelper.convertStringNumberToTemperature(response.getDeviceResultDto().getStatusDtos().get(0).getValue()));
        tempSensorModel.setHumidity(response.getDeviceResultDto().getStatusDtos().get(1).getValue());
        return objectMapper.writeValueAsString(tempSensorModel);
    }

    @RequestMapping(value = "/temp-sensor/2", produces = MediaType.APPLICATION_JSON_VALUE)
    public String tempSensorBedroom() throws IOException, InterruptedException {
        final AccessTokenResponseDto accessTokenResult = objectMapper.readValue(accessToken().getBody(), AccessTokenResponseDto.class);
        final DeviceResponseDto response = requestTempSensorHandler.getTempSensor(accessTokenResult.getAccessTokenResultDto().getAccessToken(), tuyaConfiguration.getBedroomTempSensor());
        TempSensorModel tempSensorModel = new TempSensorModel();
        tempSensorModel.setTemperature(stringHelper.convertStringNumberToTemperature(response.getDeviceResultDto().getStatusDtos().get(0).getValue()));
        tempSensorModel.setHumidity(response.getDeviceResultDto().getStatusDtos().get(1).getValue());
        return objectMapper.writeValueAsString(tempSensorModel);
    }

}