package com.iitgn.entryexit.services;
import com.iitgn.entryexit.entities.UserVisitorLog;

import java.util.List;

public interface UserVisitorLogService {
    void saveUserVisitorLog(UserVisitorLog userVisitorLog);

    UserVisitorLog findById(Long id);
    
    void deleteUserVisitorLog(Long id);
    
    List<UserVisitorLog> findAllUserVisitorLogs(int offset, int limit);

    List<UserVisitorLog> findUserVisitorLogByUserId(Long id);

    void deleteAllUserVisitorLogs();

    List<UserVisitorLog> findAllUserVisitorLogs();
}
