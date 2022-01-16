package com.zpi.transfergenerator.payment;

import org.springframework.stereotype.Component;

@Component
public class RandomNumericStringProvider {
    private static final char[] STR = "0123456789".toCharArray();

    public String getRandomString(int length) {
        final var sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            final var index = (int) (Math.random() * STR.length);
            sb.append(STR[index]);
        }
        return sb.toString();
    }
}
