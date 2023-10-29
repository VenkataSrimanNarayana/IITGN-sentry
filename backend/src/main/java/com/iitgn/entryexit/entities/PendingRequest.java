package com.iitgn.entryexit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

//Pending Request Table:
        // pending_request(request_id, Valid_from_time, Valid_from_date, Valid_upto_time, Valid_upto_date, req_type)

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pending_request")
public class PendingRequest {
    @Id
    private long requestId;

    private LocalTime validFromTime;

    private LocalDate validFromDate;

    private LocalTime validUptoTime;

    private LocalDate validUptoDate;

    private String requestType;

    private boolean status;

    private String reason;
}
