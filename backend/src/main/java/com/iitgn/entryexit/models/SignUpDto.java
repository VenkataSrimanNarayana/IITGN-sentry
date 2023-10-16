package com.iitgn.entryexit.models;

import lombok.Data;

@Data
public class SignUpDto {
    private long id;
    private String name;
    private String email;
    private String password;
}