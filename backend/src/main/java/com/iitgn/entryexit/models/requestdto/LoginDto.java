package com.iitgn.entryexit.models.requestdto;

import lombok.Data;

@Data
public class LoginDto {
    private long userId;
    private String password;
}