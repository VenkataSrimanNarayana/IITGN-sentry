package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.UserVehicleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserVehicleLogRepository extends JpaRepository<UserVehicleLog, UUID> {

    @Query("SELECT u FROM UserVehicleLog u WHERE u.user.id = ?1")
    List<UserVehicleLog> findByUserId(Long id);
}
