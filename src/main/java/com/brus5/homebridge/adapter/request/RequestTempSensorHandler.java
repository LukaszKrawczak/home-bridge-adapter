package com.brus5.homebridge.adapter.request;

import com.brus5.homebridge.adapter.model.device.DeviceResponseDto;
import java.io.IOException;

public interface RequestTempSensorHandler {
    DeviceResponseDto getTempSensor(String accessToken, String deviceId) throws IOException, InterruptedException;
}
