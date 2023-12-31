package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.models.responses.JWTAuthResponse;
import com.iitgn.entryexit.models.requestdto.LoginDto;
import com.iitgn.entryexit.models.requestdto.SignUpDto;
import com.iitgn.entryexit.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/* Open Endpoints */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // login endpoint
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticate(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

//    @PreAuthorize("hasAuthority('ACCOUNT_SIGNUP_PRIVILEGE')")
    // signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto){
        String response = authService.userSignup(signUpDto);
        if(response.equals("User Registered Successfully")){
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // test endpoint
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test(){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello World");
        return ResponseEntity.ok().body(response);
    }
}