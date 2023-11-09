package com.iitgn.entryexit.entities;

//Maid Table:
//        maid(worker_id, first_name, last_name, house_no, area, Landmark, Pin_code, Town_city, State, Country, work_doing, mobile_no)

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "maid")
public class Maid {

    @Id
    private long workerId;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 10, nullable = false)
    private String houseNo;

    @Column(length = 30, nullable = false)
    private String area;

    @Column(length = 30, nullable = false)
    private String landmark;

    @Column(nullable = false)
    private int pinCode;

    @Column(length = 30, nullable = false)
    private String townCity;

    @Column(length = 30, nullable = false)
    private String state;

    @Column(length = 30, nullable = false)
    private String country;

    @Column(length = 30, nullable = false)
    private String workDoing;

    @Column(length = 10, nullable = false)
    private String mobileNo;

    @OneToMany
    @JoinColumn(name = "worker_id")
    private Set<MaidLog> maidLogs;

}
