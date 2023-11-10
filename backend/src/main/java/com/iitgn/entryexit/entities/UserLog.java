package com.iitgn.entryexit.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

// user_logs(user_id, room_no, block_no, purpose, is_entry, date, time, vehicle_no)

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_log")
public class UserLog {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID userLogId;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false)
    private boolean isEntry;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate eventDate;

    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime eventTime;

    @Column(nullable = false)
    private String vehicleNo;

    @Column(nullable = false)
    private String blockName;

    @Column(nullable = false)
    private int roomNo;

    @ManyToOne
    private User user;

}
