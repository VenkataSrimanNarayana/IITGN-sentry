package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.MaidLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface MaidLogRepository extends JpaRepository<MaidLog, UUID>{
    @Query("SELECT m FROM MaidLog m WHERE m.maid.user = ?1")
    MaidLog findMaidLogByMaidId(Long id);
}
