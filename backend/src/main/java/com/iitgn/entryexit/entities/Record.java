package com.iitgn.entryexit.entities;

import com.iitgn.entryexit.models.EnumModels.EntryExit;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int memberId;
    @Enumerated(EnumType.ORDINAL)
    private EntryExit type;
    private String inCampusAddress;
    private Date date;
    private String visitorType;
}
