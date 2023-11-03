package com.iitgn.entryexit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

// Vehicle Visitor Logs Table:
//        vehicle_visitor_logs(vehicle_no, first_name, last_name, mobile_no, remarks, in_date, in_time, out_date, out_time)

@AllArgsConstructor
@Setter
@Builder
@Entity
@Getter
@NoArgsConstructor
public class VehicleVisitorLog {

    @Id
    @OneToOne
    private VisitorLog visitorLog;

    private String vehicleNo;

    private String firstName;

    private String lastName;

    private long mobileNo;

    private String remarks;

    private LocalDate inDate;

    private LocalTime inTime;

    private LocalDate outDate;

    private LocalTime outTime;
}
