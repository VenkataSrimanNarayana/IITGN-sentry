package com.iitgn.entryexit.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@Entity
public class VisitorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long visitorLongId;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false)
    private String vehicleNo;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate inDate;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime inTime;

    private LocalDate outDate;

    private LocalTime outTime;
}
