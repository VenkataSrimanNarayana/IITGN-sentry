package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.Maid;

import java.util.List;
import java.util.UUID;

public interface MaidService {
    void saveMaid(Maid maid);

    Maid getMaid(UUID id);


    void deleteMaid(UUID id);

    List<Maid> getAllMaid();
}
