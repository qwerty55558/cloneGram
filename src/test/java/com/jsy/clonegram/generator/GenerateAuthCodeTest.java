package com.jsy.clonegram.generator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
class GenerateAuthCodeTest {
    @Test
    void generatedAuthCode() throws InterruptedException {
        GenerateAuthCode code = new GenerateAuthCode();

        for (int i = 0; i < 200; i++) {
            methodtest(code);
            Thread.sleep(100);
        }
    }

    private static void methodtest(GenerateAuthCode code) {
        String s = code.executeGenerate();
        log.info("s = {}",s);
    }
}