package com.iitgn.entryexit.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponse {
    private String role;
    private int id;
}
