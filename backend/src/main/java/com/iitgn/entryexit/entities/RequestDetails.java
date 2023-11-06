package com.iitgn.entryexit.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


//Request Details Table:
//        req_details(request_id, first_name, last_name, house_no, area, Landmark, Pincode, Town_city, State, Country, mobile_no, vehicle_no)


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RequestDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long requestId;

    private String firstName;

    private String lastName;

    private String houseNo;

    private String area;

    private String landmark;

    private int pinCode;

    private String townCity;

    private String state;

    private String country;

    private String mobileNo;

    private String vehicleNo;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pending_request_id")
    private PendingRequest pendingRequest;
}
