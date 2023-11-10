package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.VisitorRequestDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestDetailsRepository extends JpaRepository<VisitorRequestDetails, Long> {
}
