package com.iitgn.entryexit.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long VehicleUserLogId;

    @Column(nullable = false)
    private String vehicleNo;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, length = 10)
    private String mobileNo;

    @Column(nullable = false, length = 1000)
    private boolean isPickup;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate inDate;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime inTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate outDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime outTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

}
