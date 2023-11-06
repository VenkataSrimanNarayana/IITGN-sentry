package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.UserLog;
import com.iitgn.entryexit.repositories.UserLogRepository;
import com.iitgn.entryexit.services.UserLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserLogServiceImpl implements UserLogService {

    private final UserLogRepository userLogRepository;

    @Override
    public void saveUserLog(UserLog userLog) {
        userLogRepository.save(userLog);
    }

    @Override
    public void deleteUserLog(Long id) {
        userLogRepository.deleteById(id);
    }
}
