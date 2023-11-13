package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.Maid;
import com.iitgn.entryexit.repositories.MaidRepository;
import com.iitgn.entryexit.services.MaidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaidServiceImpl implements MaidService {

    private final MaidRepository maidRepository;

    @Override
    public void saveMaid(Maid maid) {
        maidRepository.save(maid);
    }

    @Override
    public Maid getMaid(UUID id) {
        return maidRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMaid(UUID id) {
        maidRepository.deleteById(id);
    }

    @Override
    public List<Maid> getAllMaid() {
        return maidRepository.findAll();
    }
}
