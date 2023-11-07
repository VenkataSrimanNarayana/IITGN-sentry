package com.iitgn.entryexit.models.id;

import lombok.Setter;
import java.io.Serializable;

@Setter
public class ContactNumberId implements Serializable {
    private String type;
    private String phone;
}
