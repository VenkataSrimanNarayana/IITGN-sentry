package com.iitgn.entryexit.models.requestdto;

import lombok.Data;

//Maid Table:
//        maid(worker_id, first_name, last_name, house_no, area, Landmark, Pin_code, Town_city, State, Country, work_doing, mobile_no)


@Data
public class MaidDto {
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
}
