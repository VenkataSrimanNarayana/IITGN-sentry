package com.iitgn.entryexit.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogResponse {

    private long userLogId;

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
