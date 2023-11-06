package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.UserVisitorLog;
import com.iitgn.entryexit.repositories.UserVisitorLogRepository;
import com.iitgn.entryexit.services.UserVisitorLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserVisitorLogServiceImpl implements UserVisitorLogService {

    private final UserVisitorLogRepository userVisitorLogRepository;

    @Override
    public void saveUserVisitorLog(UserVisitorLog userVisitorLog) {
        userVisitorLogRepository.save(userVisitorLog);
    }
}
