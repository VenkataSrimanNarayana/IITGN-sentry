package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.UserVehicleLog;

import java.util.List;

public interface UserVehicleLogService {
    void saveUserVehicleLog(UserVehicleLog userVehicleLog);

    UserVehicleLog findById(Long id);

    void deleteUserVehicleLog(Long id);

    void deleteAllUserVehicleLogs();

    List<UserVehicleLog> findUserVehicleLogByUserId(Long id);

    List<UserVehicleLog> findAllUserVehicleLogs();
}
