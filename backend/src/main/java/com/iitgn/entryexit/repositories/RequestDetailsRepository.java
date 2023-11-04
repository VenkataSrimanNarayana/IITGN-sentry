package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.RequestDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestDetailsRepository extends JpaRepository<RequestDetails, Long> {
}
