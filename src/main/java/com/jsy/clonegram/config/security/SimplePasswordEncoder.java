package com.jsy.clonegram.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * PassWordEncoder를 직접 구현해서 사용할 수 있음
 * encode로 암호화 하고 matches로 rawpassword와 encodedpassword를 비교할 수 있음.
 */
public class SimplePasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
