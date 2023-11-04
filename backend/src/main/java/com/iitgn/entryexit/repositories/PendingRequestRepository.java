package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.PendingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingRequestRepository extends JpaRepository<PendingRequest, Long> {

}
