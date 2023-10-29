package com.iitgn.entryexit.models.id;


import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Setter
public class UserLogId implements Serializable {
    private boolean isEntry;
    private LocalDate eventDate;
    private LocalTime eventTime;
}
