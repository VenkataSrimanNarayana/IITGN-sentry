package com.iitgn.entryexit.entities;

import jakarta.persistence.*;
import lombok.*;

;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "roles")
public class Role {

    @Id
    private long id;

    @Column(length = 60)
    private String name;
}
