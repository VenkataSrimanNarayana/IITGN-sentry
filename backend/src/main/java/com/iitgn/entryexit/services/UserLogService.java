package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.UserLog;
import com.iitgn.entryexit.models.responses.UserLogResponse;

import java.util.List;

public interface UserLogService {
    void saveUserLog(UserLog userLog);

    void deleteUserLog(Long id);

    List<UserLogResponse> getAllLogs();

    List<UserLogResponse> getAllLogsOfUser(Long userId);
}
