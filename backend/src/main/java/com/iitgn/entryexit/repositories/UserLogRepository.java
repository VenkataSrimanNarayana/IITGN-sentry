package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {

    @Query("SELECT ul FROM UserLog ul WHERE ul.user.id = ?1")
    List<UserLog> findAllByUserId(Long userId);
}
