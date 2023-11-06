package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.UserVisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserVisitorLogRepository extends JpaRepository<UserVisitorLog, Long> {


    @Query("SELECT u FROM UserVisitorLog u WHERE u.user.id = ?1")
    List<UserVisitorLog> findByUserId(Long id);
}
