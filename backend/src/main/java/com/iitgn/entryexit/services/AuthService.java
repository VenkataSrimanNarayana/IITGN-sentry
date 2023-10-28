package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.LoginDto;
import com.iitgn.entryexit.models.SignUpDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String userSignup(SignUpDto signUpDto);

//    String securitySignUp(SignUpDto signUpDto);
}