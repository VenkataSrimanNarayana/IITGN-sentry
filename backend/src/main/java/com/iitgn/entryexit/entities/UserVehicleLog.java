package com.iitgn.entryexit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

// Vehicle Visitor Logs Table:
// vehicle_visitor_logs(vehicle_no, user_id, first_name, last_name, mobile_no, remarks, in_date, in_time, out_date, out_time)

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVehicleLog {

    @Id
    private long VehicleUserLogId;

    private String vehicleNo;

    private String firstName;

    private String lastName;

    private String mobileNo;

    private boolean isPickup;

    private LocalDate inDate;

    private LocalTime inTime;

    private LocalDate outDate;

    private LocalTime outTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

}