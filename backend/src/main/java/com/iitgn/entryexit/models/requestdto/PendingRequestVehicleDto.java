package com.iitgn.entryexit.models.requestdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PendingRequestVehicleDto {
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

    private String mobileNo;

    private String vehicleNo;

    private boolean isPickUp;
}
