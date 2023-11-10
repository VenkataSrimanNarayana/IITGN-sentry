package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.UserVisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserVisitorLogRepository extends JpaRepository<UserVisitorLog, UUID> {


    @Query("SELECT u FROM UserVisitorLog u WHERE u.user.id = ?1")
    List<UserVisitorLog> findByUserId(Long id);
}
