package com.iitgn.entryexit.models.requestdto;

import lombok.Data;

@Data
public class PasswordChangeRequestDto {
    String newPassword;
    String oldPassword;
}
