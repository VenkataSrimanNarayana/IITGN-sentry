package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.UserLog;
import com.iitgn.entryexit.models.responses.UserLogResponse;
import com.iitgn.entryexit.repositories.UserLogRepository;
import com.iitgn.entryexit.services.UserLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserLogServiceImpl implements UserLogService {

    private final UserLogRepository userLogRepository;

    @Override
    public void saveUserLog(UserLog userLog) {
        userLogRepository.save(userLog);
    }

    @Override
    public void deleteUserLog(UUID id) {
        userLogRepository.deleteById(id);
    }

    @Override
    public List<UserLogResponse> getAllLogs() {
        return userLogRepository.findCustomDataFromUserLog();
    }

    @Override
    public List<UserLogResponse> getAllLogsOfUser(Long userId) {
        return userLogRepository.findAllByUserId(userId);
    }
}
