package com.iitgn.entryexit.entities;

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

    private LocalDate inDate;

    private LocalTime inTime;

    private LocalDate outDate;

    private LocalTime outTime;
}
