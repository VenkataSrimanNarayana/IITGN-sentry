package com.iitgn.entryexit.entities;

// vehicle_visitor_logs(vehicle_no, visitor_id, first_name, last_name, mobile_no, remarks, in_date, in_time, out_date, out_time)

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table()
public class UserVisitorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userVisitorLogId;

    private String vehicleNo;

    private String firstName;

    private String lastName;

    private String mobileNo;

    private String purpose;

    private String houseNo;

    private String area;

    private String landmark;

    private int pinCode;

    private String townCity;

    private String state;

    private String country;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate inDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate outDate;

    @JsonFormat(pattern="HH:mm")
    private LocalTime inTime;

    @JsonFormat(pattern="HH:mm")
    private LocalTime outTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

}
