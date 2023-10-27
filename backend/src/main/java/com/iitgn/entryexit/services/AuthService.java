package com.iitgn.entryexit.services;

import com.iitgn.entryexit.models.LoginDto;
import com.iitgn.entryexit.models.SignUpDto;
import com.iitgn.entryexit.models.SingleLineErrorResponse;

public interface AuthService {
    String login(LoginDto loginDto);

    String userSignup(SignUpDto signUpDto);

//    String securitySignUp(SignUpDto signUpDto);
}