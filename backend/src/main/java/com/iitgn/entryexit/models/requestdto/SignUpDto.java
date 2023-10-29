package com.iitgn.entryexit.models.requestdto;

import lombok.Data;
// user(user_id, first_name, last_name, house_no, area, Landmark, Pincode, Town_city, State, Country, Type)


@Data
public class SignUpDto {
    private long id;
    private String firstName;
    private String lastName;
    private String houseNo;
    private String area;
    private String landmark;
    private int pincode;
    private String townCity;
    private String state;
    private String country;
    private String password;
    private String userType;
    private String role;
    private String mobileNo;
    private String email;
}