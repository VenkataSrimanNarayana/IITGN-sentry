package com.iitgn.entryexit.models.requestdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PendingRequestOtherDto {
    @JsonFormat(pattern="HH:mm")
    private LocalTime validFromTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate validFromDate;
    @JsonFormat(pattern="HH:mm")
    private LocalTime validUptoTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate validUptoDate;
    private String reason;

    private String firstName;

    private String lastName;

    private String houseNo;

    private String area;

    private String landmark;

    private int pinCode;

    private String townCity;

    private String state;

    private String country;

    private String mobileNo;

    private String vehicleNo;
}
