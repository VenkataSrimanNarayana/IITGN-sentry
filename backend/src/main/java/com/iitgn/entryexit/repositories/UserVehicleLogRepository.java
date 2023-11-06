package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.UserVehicleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserVehicleLogRepository extends JpaRepository<UserVehicleLog, Long> {

    @Query("SELECT u FROM UserVehicleLog u WHERE u.user.id = ?1")
    List<UserVehicleLog> findByUserId(Long id);
}
