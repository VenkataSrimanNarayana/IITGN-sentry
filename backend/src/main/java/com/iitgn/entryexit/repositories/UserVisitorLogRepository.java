package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.UserVisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVisitorLogRepository extends JpaRepository<UserVisitorLog, Long> {

}
