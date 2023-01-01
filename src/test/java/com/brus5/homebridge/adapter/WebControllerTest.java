package com.brus5.homebridge.adapter;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
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
    void shouldThrowResponseStatusException_whenRequestCannotResolveUri_givenTmpHdmSensorUriForNothing() {
        // given
        String testedUri = "http://localhost:" + port + "/temp-sensor/SOMETHING_THAT_CANNOT_PARSE";

        // when
        final ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(testedUri, String.class);

        // then
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"salon", "bedroom"})
    void shouldReturnCorrectTemperatureAndHumidityResponse_whenExecutingGetTempSensorEndpoint_givenCorrectUri(String source) throws JSONException {
        // given
        String testedUri = "http://localhost:" + port + "/temp-sensor/" + source;

        // when
        String expected = "{" + "\"temperature\": \"23.1\","
                + "\"humidity\": \"40\""
                + "}";
        final ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(testedUri, String.class);

        // then
        JSONAssert.assertEquals(expected, responseEntity.getBody(), new CustomComparator(JSONCompareMode.LENIENT,
                ignoreFieldValue("temperature"),
                ignoreFieldValue("humidity"))
        );
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private Customization ignoreFieldValue(String ignoredFieldValue) {
        return new Customization(ignoredFieldValue, (o1, o2) -> true);
    }
}
