package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.UserLog;

import java.util.List;

public interface UserLogService {
    void saveUserLog(UserLog userLog);

    void deleteUserLog(Long id);

    List<UserLog> getAllLogs();

    List<UserLog> getAllLogsOfUser(Long userId);
}
