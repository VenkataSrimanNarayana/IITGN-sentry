package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.MaidLog;
import com.iitgn.entryexit.repositories.MaidLogRepository;
import com.iitgn.entryexit.services.MaidLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MaidLogServiceImpl implements MaidLogService {

    private final MaidLogRepository maidLogRepository;

    @Override
    public void saveMaidLog(MaidLog maidLog) {
        maidLogRepository.save(maidLog);
    }

    @Override
    public List<MaidLog> getAllMaidLog() {
        return maidLogRepository.findAll();
    }

    @Override
    public MaidLog getMaidLog(UUID id) {
        return maidLogRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMaidLog(UUID id) {
        maidLogRepository.deleteById(id);
    }

    @Override
    public MaidLog getMaidLogByUserId(Long id) {
        return maidLogRepository.findMaidLogByMaidId(id);
    }
}
