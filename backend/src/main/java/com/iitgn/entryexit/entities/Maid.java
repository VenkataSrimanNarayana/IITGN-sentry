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

    @Column(length = 50)
    private String firstName;

    private String lastName;

    private String houseNo;

    private String area;

    private String landmark;

    private int pinCode;

    private String townCity;

    private String state;

    private String country;

    private String workDoing;

    private String mobileNo;

    @OneToMany
    @JoinColumn(name = "worker_id")
    private Set<MaidLog> maidLogs;

}
