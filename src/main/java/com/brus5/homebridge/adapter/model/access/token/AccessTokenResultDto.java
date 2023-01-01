package com.brus5.homebridge.adapter.model.access.token;

import com.brus5.homebridge.adapter.model.device.StatusDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class AccessTokenResultDto {

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expire_time")
    private Long expireTime;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    private String uid;

    @NonNull
    @JsonProperty(value = "status")
    private List<StatusDto> statusDtos;
}
