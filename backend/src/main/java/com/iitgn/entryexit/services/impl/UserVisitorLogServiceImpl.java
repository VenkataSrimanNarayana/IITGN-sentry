package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.UserVisitorLog;
import com.iitgn.entryexit.repositories.UserVisitorLogRepository;
import com.iitgn.entryexit.services.UserVisitorLogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserVisitorLogServiceImpl implements UserVisitorLogService {

    private final UserVisitorLogRepository userVisitorLogRepository;


    @Override
    public void saveUserVisitorLog(UserVisitorLog userVisitorLog) {
        userVisitorLogRepository.save(userVisitorLog);
    }

    @Override
    public UserVisitorLog findById(Long id) {
        return userVisitorLogRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUserVisitorLog(Long id) {
        userVisitorLogRepository.deleteById(id);
    }

    @Override
    public List<UserVisitorLog> findAllUserVisitorLogs(int offset, int limit) {
        return userVisitorLogRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    @Override
    public List<UserVisitorLog> findUserVisitorLogByUserId(Long id) {
        return userVisitorLogRepository.findByUserId(id);
    }

    @Override
    public void deleteAllUserVisitorLogs() {
        userVisitorLogRepository.deleteAll();

    }

    @Override
    public List<UserVisitorLog> findAllUserVisitorLogs() {
        return userVisitorLogRepository.findAll();
    }

}
