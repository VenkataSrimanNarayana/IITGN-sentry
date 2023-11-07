package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.UserVehicleLog;
import com.iitgn.entryexit.repositories.UserVehicleLogRepository;
import com.iitgn.entryexit.services.UserVehicleLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserVehicleLogServiceImpl implements UserVehicleLogService {

    private final UserVehicleLogRepository userVehicleLogRepository;
    @Override
    public void saveUserVehicleLog(UserVehicleLog userVehicleLog) {
        userVehicleLogRepository.save(userVehicleLog);
    }

    @Override
    public UserVehicleLog findById(Long id) {
        return userVehicleLogRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUserVehicleLog(Long id) {
        userVehicleLogRepository.deleteById(id);
    }

    @Override
    public void deleteAllUserVehicleLogs() {
        userVehicleLogRepository.deleteAll();
    }

    @Override
    public List<UserVehicleLog> findUserVehicleLogByUserId(Long id) {
        return userVehicleLogRepository.findByUserId(id);
    }

    @Override
    public List<UserVehicleLog> findAllUserVehicleLogs() {
        return userVehicleLogRepository.findAll();
    }

}
