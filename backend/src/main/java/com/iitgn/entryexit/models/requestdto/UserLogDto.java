package com.iitgn.entryexit.models.requestdto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UserLogDto {

    private String purpose;

    private boolean isEntry;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime eventTime;

    private String vehicleNo;

    private String blockName;

    private int roomNo;

    private long userId;
}
