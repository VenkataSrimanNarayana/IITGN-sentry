package com.iitgn.entryexit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    private long visitorLongId;

    private String purpose;

    private String vehicleNo;

    private LocalDate inDate;

    private LocalTime inTime;

    private LocalDate outDate;

    private LocalTime outTime;

    @OneToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;
}
