//package com.iitgn.entryexit.entities;
//
//import com.iitgn.entryexit.models.VehicleUserLogId;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.IdClass;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.Getter;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//// Vehicle Visitor Logs Table:
//// vehicle_visitor_logs(vehicle_no, user_id, first_name, last_name, mobile_no, remarks, in_date, in_time, out_date, out_time)
//
//@Entity
//@Setter
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@IdClass(VehicleUserLogId.class)
//public class VehicleUserLog {
//
//    @Id
//    private String vehicleNo;
//
//    private String firstName;
//
//    private String lastName;
//
//    @Id
//    private long mobileNo;
//
//    private String remarks;
//
//    @Id
//    private LocalDate inDate;
//
//    @Id
//    private LocalTime inTime;
//
//    private LocalDate outDate;
//
//    private LocalTime outTime;
//
//}
