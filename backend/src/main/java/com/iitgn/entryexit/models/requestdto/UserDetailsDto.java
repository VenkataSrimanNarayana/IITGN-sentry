package com.iitgn.entryexit.models.requestdto;

import com.iitgn.entryexit.entities.ContactNumber;
import com.iitgn.entryexit.entities.Email;
import lombok.Builder;
import lombok.Data;

import java.util.List;

// user(user_id, first_name, last_name, house_no, area, Landmark, pin_code, Town_city, State, Country, Type)
@Data
@Builder
public class UserDetailsDto {
    private String houseNo;
    private String area;
    private String landmark;
    private int pinCode;
    private String townCity;
    private String state;
    private String country;
    private List<Email> emails;
    private List<ContactNumber> contactNumbers;
}
