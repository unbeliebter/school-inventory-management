package com.example.demo.service.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordHandler {
    public final int MIN_PASSWORD_LENGTH = 12;
    private static final SecureRandom random = new SecureRandom();
    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789#!=()%&";

    public String generateOneTimePassword() {
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean encodePassword(String passwordBefore, String hashed) {
        return BCrypt.checkpw(passwordBefore, hashed);
    }

    public PasswordState isPasswordValid(String pw, String pwCheck) {
        if (pw == null || pwCheck == null) {
            return PasswordState.EMPTY;
        }
        if (pw.length() < 12) {
            return PasswordState.TO_SHORT;
        }
        if (!pw.equals(pwCheck)) {
            return PasswordState.UNEQUAL;
        }

        return PasswordState.OK;
    }
}
