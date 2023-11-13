package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.MaidLog;

import java.util.List;
import java.util.UUID;

public interface MaidLogService {
    void saveMaidLog(MaidLog maidLog);

    List<MaidLog> getAllMaidLog();

    MaidLog getMaidLog(UUID id);

    void deleteMaidLog(UUID id);
}
