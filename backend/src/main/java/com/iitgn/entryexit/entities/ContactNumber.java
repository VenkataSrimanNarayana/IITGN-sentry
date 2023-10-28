package com.iitgn.entryexit.entities;

import jakarta.persistence.*;
import lombok.*;


//Contact Number Table: contact_no(user_id, phone)

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact_number")
public class ContactNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String type;

    private String phone;

}