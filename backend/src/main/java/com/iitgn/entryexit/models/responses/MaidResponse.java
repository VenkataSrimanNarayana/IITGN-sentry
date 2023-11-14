package com.iitgn.entryexit.models.responses;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MaidResponse {
    private UUID workerId;

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

    private Long UserId;

}
