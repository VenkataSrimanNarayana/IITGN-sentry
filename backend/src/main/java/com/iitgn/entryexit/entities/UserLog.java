package com.iitgn.entryexit.entities;

import jakarta.persistence.*;

import java.time.*;

import com.iitgn.entryexit.models.id.UserLogId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

// user_logs(user_id, room_no, block_no, purpose, is_entry, date, time, vehicle_no)

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_log")
@IdClass(UserLogId.class)
public class UserLog {
    private int roomNo;
    private String blockNo;
    private String purpose;

    @Id
    private boolean isEntry;

    @Id
    private LocalDate eventDate;

    @Id
    private LocalTime eventTime;
    private String vehicleNo;
}
