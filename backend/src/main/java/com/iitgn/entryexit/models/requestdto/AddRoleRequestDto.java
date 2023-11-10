package com.iitgn.entryexit.models.requestdto;

import lombok.Data;

import java.util.List;

@Data
public class AddRoleRequestDto {
    private String roleName;
    private List<Integer> privilegeIds;
}
