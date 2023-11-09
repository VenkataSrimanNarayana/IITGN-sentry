package com.iitgn.entryexit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequestDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long requestId;


    private String firstName;

    private String lastName;

    private String mobileNo;

    private String vehicleNo;

    private boolean isPickup;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pending_request_id")
    private PendingRequest pendingRequest;
}
