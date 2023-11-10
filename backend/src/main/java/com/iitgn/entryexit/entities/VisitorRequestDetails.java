package com.iitgn.entryexit.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


//Request Details Table:
//        req_details(request_id, first_name, last_name, house_no, area, Landmark, Pincode, Town_city, State, Country, mobile_no, vehicle_no)


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VisitorRequestDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID requestId;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 10)
    private String houseNo;

    @Column(length = 30)
    private String area;

    @Column(length = 30)
    private String landmark;

    @Column(nullable = false)
    private int pinCode;

    @Column(length = 30)
    private String townCity;

    @Column(length = 30, nullable = false)
    private String state;

    @Column(length = 30, nullable = false)
    private String country;

    @Column(length = 10, nullable = false)
    private String mobileNo;

    @Column(length = 10)
    private String vehicleNo;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pending_request_id")
    private PendingRequest pendingRequest;
}
