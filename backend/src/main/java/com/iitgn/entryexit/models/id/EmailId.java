package com.iitgn.entryexit.models.id;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class EmailId implements Serializable {
    private String type;
    private String email;
}
