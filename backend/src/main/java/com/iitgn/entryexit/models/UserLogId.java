package com.iitgn.entryexit.models;


import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Setter
public class UserLogId implements Serializable {

    private long userId;
    private LocalDate eventDate;
    private LocalTime eventTime;

}
