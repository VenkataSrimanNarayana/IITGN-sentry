package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.UserLog;

public interface UserLogService {
    void saveUserLog(UserLog userLog);

    void deleteUserLog(Long id);
}
