package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.MaidLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MaidLogRepository extends JpaRepository<MaidLog, UUID>{

}
