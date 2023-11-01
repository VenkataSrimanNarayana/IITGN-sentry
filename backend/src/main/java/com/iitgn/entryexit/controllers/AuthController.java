package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.models.responses.JWTAuthResponse;
import com.iitgn.entryexit.models.requestdto.LoginDto;
import com.iitgn.entryexit.models.requestdto.SignUpDto;
import com.iitgn.entryexit.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// open endpoints, anyone can access these endpoints

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @CrossOrigin(origins = "*")
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
        if(response.equals("User Registered Successfully")){
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
}