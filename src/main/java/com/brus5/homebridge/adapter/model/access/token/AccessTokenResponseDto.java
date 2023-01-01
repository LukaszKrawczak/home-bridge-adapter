package com.brus5.homebridge.adapter.model.access.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AccessTokenResponseDto {

    @JsonProperty(value = "result")
    private AccessTokenResultDto accessTokenResultDto;

    private Boolean success;

    @JsonProperty(value = "t")
    private Long timestamp;

    private String tid;
}
