package com.brus5.homebridge.adapter.model.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class DeviceResultDto {

    private String name;

    @JsonProperty(value = "id")
    private String deviceId;

    @JsonProperty(value = "product_name")
    private String productName;

    private String uid;

    @JsonProperty(value = "status")
    private List<StatusDto> statusDtos;
}
