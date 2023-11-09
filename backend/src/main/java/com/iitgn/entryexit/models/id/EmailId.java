package com.iitgn.entryexit.models.id;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailId implements Serializable {
    private String type;
    private String email;
}
