package com.iitgn.entryexit.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "privilege",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;
}
