package com.iitgn.entryexit.models.id;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class VehicleUserLogId implements Serializable {

    private String vehicleNo;

    private long mobileNo;

    private LocalDate inDate;

    private LocalTime inTime;
}
