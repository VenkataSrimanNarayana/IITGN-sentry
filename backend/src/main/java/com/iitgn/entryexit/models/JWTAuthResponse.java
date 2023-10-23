package com.iitgn.entryexit.models;
import lombok.*;

@Data
public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
}