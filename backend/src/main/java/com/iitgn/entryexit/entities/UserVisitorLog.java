package com.iitgn.entryexit.entities;

// vehicle_visitor_logs(vehicle_no, visitor_id, first_name, last_name, mobile_no, remarks, in_date, in_time, out_date, out_time)

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVisitorLog {

    private String vehicleNo;

    private String firstName;

    private String lastName;

    private long mobileNo;

    private String remarks;

    private String purpose;

    private long visitorId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userVisitorLogId;
}
