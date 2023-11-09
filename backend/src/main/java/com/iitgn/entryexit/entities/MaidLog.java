package com.iitgn.entryexit.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


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
    private long maidLogId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate inDate;

    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime inTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate outDate;

    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime outTime;
}
