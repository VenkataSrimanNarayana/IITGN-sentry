package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.VisitorRequestDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequestDetailsRepository extends JpaRepository<VisitorRequestDetails, UUID> {
}
