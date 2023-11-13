package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface PendingRequestRepository extends JpaRepository<PendingRequest, UUID> {


    @Transactional
    @Query("SELECT p FROM PendingRequest p WHERE p.user = ?1")
    List<PendingRequest> findByUser(User user);

    List<PendingRequest> findPendingRequestByValidUptoDateAfterAndValidUptoTimeAfter(LocalDate date, LocalTime time);
}
