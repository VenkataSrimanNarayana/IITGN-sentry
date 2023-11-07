package com.iitgn.entryexit.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

// user_logs(user_id, room_no, block_no, purpose, is_entry, date, time, vehicle_no)

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_log")
public class UserLog {

    @Id
    private long userLogId;

    private String purpose;

    private boolean isEntry;

    private LocalDate eventDate;

    private LocalTime eventTime;

    private String vehicleNo;

    private String blockName;

    private int roomNo;

    @ManyToOne
    private User user;

}
