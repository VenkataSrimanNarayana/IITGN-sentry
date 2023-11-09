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

    @Column
    private String vehicleNo;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 10, nullable = false)
    private String mobileNo;

    @Column(length = 1000)
    private String purpose;

    @Column(length = 1000)
    private String houseNo;

    @Column(length = 1000)
    private String area;

    @Column(length = 1000)
    private String landmark;

    @Column(nullable = false)
    private int pinCode;

    @Column(length = 1000)
    private String townCity;

    @Column(length = 1000, nullable = false)
    private String state;

    @Column(length = 1000, nullable = false)
    private String country;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate inDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate outDate;

    @Column(nullable = false)
    @JsonFormat(pattern="HH:mm")
    private LocalTime inTime;

    @JsonFormat(pattern="HH:mm")
    private LocalTime outTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

}
