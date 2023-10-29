package com.iitgn.entryexit.models.responses;
import lombok.*;

@Data
public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
}