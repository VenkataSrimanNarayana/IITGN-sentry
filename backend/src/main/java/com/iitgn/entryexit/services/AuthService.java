package com.iitgn.entryexit.services;

import com.iitgn.entryexit.models.requestdto.LoginDto;
import com.iitgn.entryexit.models.requestdto.SignUpDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String userSignup(SignUpDto signUpDto);

//    String securitySignUp(SignUpDto signUpDto);
}