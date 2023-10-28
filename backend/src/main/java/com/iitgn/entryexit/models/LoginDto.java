package com.iitgn.entryexit.models;

import lombok.Data;

@Data
public class LoginDto {
    private long userId;
    private String password;
}