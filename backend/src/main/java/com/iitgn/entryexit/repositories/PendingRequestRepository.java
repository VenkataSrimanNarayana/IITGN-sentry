package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PendingRequestRepository extends JpaRepository<PendingRequest, Long> {


    @Transactional
    @Query("SELECT p FROM PendingRequest p WHERE p.user = ?1")
    List<PendingRequest> findByUser(User user);

}
