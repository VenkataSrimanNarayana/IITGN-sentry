package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.VehicleRequestDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VehicleRequestDetailsRepository extends JpaRepository<VehicleRequestDetails, UUID> {
}
