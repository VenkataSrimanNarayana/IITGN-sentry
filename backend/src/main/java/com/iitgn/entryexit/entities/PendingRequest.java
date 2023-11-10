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
import java.util.UUID;

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

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID requestId;

    private LocalTime validFromTime;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate validFromDate;

    @JsonFormat(pattern="HH:mm")
    private LocalTime validUptoTime;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate validUptoDate;

    @Column(nullable = false)
    private String requestType;

    @Column(length = 1000)
    private String reason;

    @Column(nullable = false)
    private boolean isEntry;

    @JsonIgnore
    @ManyToOne
    private User user;


    private String vehicleNo;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_visitor_log_id")
    private UserVisitorLog userVisitorLog;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_vehicle_log_id")
    private UserVehicleLog userVehicleLog;


    @OneToOne(mappedBy = "pendingRequest", cascade = CascadeType.ALL)
    private VisitorRequestDetails visitorRequestDetails;

    @OneToOne(mappedBy = "pendingRequest", cascade = CascadeType.ALL)
    private VehicleRequestDetails vehicleRequestDetails;
}





