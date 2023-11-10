package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.UserVehicleLog;
import com.iitgn.entryexit.repositories.UserVehicleLogRepository;
import com.iitgn.entryexit.services.UserVehicleLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserVehicleLogServiceImpl implements UserVehicleLogService {

    private final UserVehicleLogRepository userVehicleLogRepository;
    @Override
    public void saveUserVehicleLog(UserVehicleLog userVehicleLog) {
        userVehicleLogRepository.save(userVehicleLog);
    }

    @Override
    public UserVehicleLog findById(UUID id) {
        return userVehicleLogRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUserVehicleLog(UUID id) {
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
