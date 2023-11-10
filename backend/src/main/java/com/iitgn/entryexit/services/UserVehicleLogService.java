package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.UserVehicleLog;

import java.util.List;
import java.util.UUID;

public interface UserVehicleLogService {
    void saveUserVehicleLog(UserVehicleLog userVehicleLog);

    UserVehicleLog findById(UUID id);

    void deleteUserVehicleLog(UUID id);

    void deleteAllUserVehicleLogs();

    List<UserVehicleLog> findUserVehicleLogByUserId(Long id);

    List<UserVehicleLog> findAllUserVehicleLogs();
}
