package com.paramonau.pay4j.util;

import org.mindrot.jbcrypt.BCrypt;

public class StringEncryptor {

    private static final StringEncryptor instance = new StringEncryptor();

    private StringEncryptor() {
    }

    public static StringEncryptor getInstance() {
        return instance;
    }

    public boolean checkHash(String string, String hashed) {
        return BCrypt.checkpw(string, hashed);
    }

    public String getHash(String string) {
        return BCrypt.hashpw(string, BCrypt.gensalt());
    }

}
