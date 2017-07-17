package com.gani.services.security;

/**
 * Created by Gani on 7/13/17.
 */
public interface EncryptionService {

    String encryptString(String input);
    boolean checkPassword(String plainPassword, String encryptedPassword);
}
