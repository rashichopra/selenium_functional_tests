package com.apple.util;

import org.jasypt.util.text.BasicTextEncryptor;

public class Decryption {

	static BasicTextEncryptor textEncryptor;

    /*Set your MasterKey here for encryption*/
    static {
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("selenium");
    }

    /**
     * Decrypt any encrypted string
     * @param s an    encrypted string
     * @return the   decrypted result
     */
    public static String decrypt(String s) {
        return textEncryptor.decrypt(s);
    }


}
