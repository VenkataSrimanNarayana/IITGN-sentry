package com.iitgn.entryexit.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


//Maid Logs Table:
//        maid_logs(worker_id, in_date, in_time, out_date, out_time)


@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaidLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID maidLogId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate eventDate;

    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime eventTime;

    @Column(nullable = false)
    private boolean isEntry;

    @ManyToOne
    private Maid maid;
}
