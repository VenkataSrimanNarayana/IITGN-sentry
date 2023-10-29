package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.models.JWTAuthResponse;
import com.iitgn.entryexit.models.LoginDto;
import com.iitgn.entryexit.models.SignUpDto;
import com.iitgn.entryexit.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticate(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto){
        String response = authService.userSignup(signUpDto);
        return ResponseEntity.ok(response);
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/security/signup")
//    public ResponseEntity<String> registerSecurity(@RequestBody SignUpDto signUpDto){
//        String response = authService.securitySignUp(signUpDto);
//        return ResponseEntity.ok(response);
//    }




}