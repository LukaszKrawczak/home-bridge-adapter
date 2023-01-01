package com.brus5.homebridge.adapter.model.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DeviceResponseDto {

    @JsonProperty(value = "result")
    private DeviceResultDto deviceResultDto;

    private Boolean success;

    @JsonProperty(value = "t")
    private Long timestamp;

    private String tid;
}
