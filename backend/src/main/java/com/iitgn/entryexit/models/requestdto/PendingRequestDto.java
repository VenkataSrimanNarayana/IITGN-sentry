package com.iitgn.entryexit.models.requestdto;

import lombok.Data;

@Data
public class PendingRequestDto {
    private String validFromTime;
    private String validFromDate;
    private String validUptoTime;
    private String validUptoDate;
    private String requestType;
    private String reason;
}
