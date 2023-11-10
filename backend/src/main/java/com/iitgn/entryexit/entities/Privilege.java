package com.iitgn.entryexit.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(length = 100, nullable = false)
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;
}
