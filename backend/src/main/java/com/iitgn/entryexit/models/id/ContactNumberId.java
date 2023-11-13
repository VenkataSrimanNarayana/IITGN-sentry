package com.iitgn.entryexit.models.id;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import java.io.Serializable;

@Setter
@EqualsAndHashCode
public class ContactNumberId implements Serializable {
    private String type;
    private String phone;
}
