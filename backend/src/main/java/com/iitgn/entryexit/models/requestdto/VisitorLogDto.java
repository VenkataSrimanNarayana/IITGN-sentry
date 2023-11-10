package com.iitgn.entryexit.models.requestdto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class VisitorLogDto {

    private long visitorLongId;

    private String purpose;

    private String vehicleNo;

}
