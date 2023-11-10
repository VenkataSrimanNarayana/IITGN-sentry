package com.iitgn.entryexit.services;
import com.iitgn.entryexit.entities.UserVisitorLog;

import java.util.List;
import java.util.UUID;

public interface UserVisitorLogService {
    void saveUserVisitorLog(UserVisitorLog userVisitorLog);

    UserVisitorLog findById(UUID id);
    
    void deleteUserVisitorLog(UUID id);
    
    List<UserVisitorLog> findAllUserVisitorLogs(int offset, int limit);

    List<UserVisitorLog> findUserVisitorLogByUserId(Long id);

    void deleteAllUserVisitorLogs();

    List<UserVisitorLog> findAllUserVisitorLogs();
}
