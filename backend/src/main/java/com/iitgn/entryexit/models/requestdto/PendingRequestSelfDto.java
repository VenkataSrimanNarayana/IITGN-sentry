package com.iitgn.entryexit.models.requestdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PendingRequestSelfDto {
    @JsonFormat(pattern="HH:mm")
    private LocalTime validFromTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate validFromDate;
    @JsonFormat(pattern="HH:mm")
    private LocalTime validUptoTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate validUptoDate;
    private String reason;
}
