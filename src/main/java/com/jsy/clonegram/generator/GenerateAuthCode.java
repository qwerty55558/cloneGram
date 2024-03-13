package com.jsy.clonegram.generator;


import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class GenerateAuthCode {
    private int pwLength = 8;

    private final char[] passwordTable =
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&', '*',
            '(', ')', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

    public String executeGenerate() {
        Random random = new Random(System.currentTimeMillis());
        int tableLength = passwordTable.length;
        StringBuffer buf = new StringBuffer();

        for(int i = 0; i < pwLength; i++) {
            buf.append(passwordTable[random.nextInt(tableLength)]);
        }

        return buf.toString();
    }
}
