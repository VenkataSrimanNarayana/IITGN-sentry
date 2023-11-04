package com.iitgn.entryexit.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long requestId;

    private LocalTime validFromTime;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate validFromDate;

    private LocalTime validUptoTime;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate validUptoDate;

    private String requestType;

    private String reason;

    @OneToOne(cascade = CascadeType.ALL)
    private RequestDetails requestDetails;

    @JsonIgnore
    @ManyToOne
    private User user;

}
