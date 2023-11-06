package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {

}
