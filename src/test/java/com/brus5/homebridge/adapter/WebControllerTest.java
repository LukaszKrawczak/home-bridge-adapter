package com.brus5.homebridge.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class WebControllerTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnTrue_whenRequestForTokenIsCorrect_givenAccessTokenUri() {
        // given
        String testedUri = "http://localhost:" + port + "/access-token";

        // when
        final ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(testedUri,
                String.class);

        // then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).contains("\"success\":true");
    }

    @Test
    void shouldReturnTrue_whenRequestForDeviceIsCorrect_givenTmpHmdSensorUri() {
        // given
        String testedUri = "http://localhost:" + port + "/temp-sensor/2";

        // when
        final ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(testedUri,
                String.class);

        // then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).contains("temperature");
        assertThat(responseEntity.getBody()).contains("humidity");
    }
}
