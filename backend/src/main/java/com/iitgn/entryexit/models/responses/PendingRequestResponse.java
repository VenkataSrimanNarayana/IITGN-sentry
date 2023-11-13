package com.iitgn.entryexit.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iitgn.entryexit.entities.VehicleRequestDetails;
import com.iitgn.entryexit.entities.VisitorRequestDetails;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
public class PendingRequestResponse {

    private UUID requestId;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime validFromTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate validFromDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime validUptoTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate validUptoDate;

    private String requestType;

    private String reason;

    private boolean isEntry;

    private Long userId;

    private String vehicleNo;

    private VisitorRequestDetails visitorRequestDetails;

    private VehicleRequestDetails vehicleRequestDetails;
}
