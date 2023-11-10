package com.iitgn.entryexit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequestDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID requestId;


    private String firstName;

    private String lastName;

    private String mobileNo;

    private String vehicleNo;

    @Column(nullable = false)
    private boolean isPickup;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pending_request_id")
    private PendingRequest pendingRequest;
}
