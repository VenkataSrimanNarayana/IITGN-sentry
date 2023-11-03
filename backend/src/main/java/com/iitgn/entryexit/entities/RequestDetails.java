package com.iitgn.entryexit.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    @JoinColumn
    @OneToOne
    private PendingRequest pendingRequest;

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
}
