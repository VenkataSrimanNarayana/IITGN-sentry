package com.iitgn.entryexit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;



//Visitor Table:
//        visitor(visitor_id, first_name, last_name, mobile_no, house_no, area, Landmark, Pincode, Town_city, State, Country)

@Entity
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Visitor {

    @Id
    private long visitorId;

    private String firstName;

    private String lastName;

    private String mobileNo;

    private String houseNo;

    private String area;

    private String landmark;

    private int pincode;

    private String townCity;

    private String state;

    private String country;


}
