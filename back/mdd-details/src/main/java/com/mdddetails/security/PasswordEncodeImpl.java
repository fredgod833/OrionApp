package com.mdddetails.security;

import com.mddinfrastructure.security.IPasswordEncode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordEncodeImpl implements IPasswordEncode {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordEncodeImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String encodePass(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
