package com.iitgn.entryexit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;



//Visitor Table:
//        visitor(visitor_id, first_name, last_name, mobile_no, house_no, area, Landmark, PinCode, Town_city, State, Country)

@Entity
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Visitor {

    @Id
    private long visitorId;

}
