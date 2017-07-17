package com.gani.services.security;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Gani on 7/13/17.
 */

@Service
public class EncryptionServiceImpl implements EncryptionService {

    private StrongPasswordEncryptor strongEncryptor;

    @Autowired
    public void setStrongEncryptor(StrongPasswordEncryptor strongEncryptor) {
        this.strongEncryptor = strongEncryptor;
    }

    @Override
    public String encryptString(String input) {
        return strongEncryptor.encryptPassword(input);
    }

    @Override
    public boolean checkPassword(String plainPassword, String encryptedPassword) {
        return strongEncryptor.checkPassword(plainPassword,encryptedPassword);
    }
}
