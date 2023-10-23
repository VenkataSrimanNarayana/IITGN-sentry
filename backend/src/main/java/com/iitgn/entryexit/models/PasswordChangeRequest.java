package com.iitgn.entryexit.models;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    String newPassword;
    String oldPassword;
}
