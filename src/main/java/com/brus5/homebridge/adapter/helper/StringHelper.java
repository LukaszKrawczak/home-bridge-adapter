package com.brus5.homebridge.adapter.helper;

import org.springframework.stereotype.Component;

@Component
public class StringHelper {

    public String convertStringNumberToTemperature(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.insert(2, ".");
        return sb.toString();
    }
}
