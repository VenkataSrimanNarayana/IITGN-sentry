package com.iitgn.entryexit.models;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}