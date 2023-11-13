package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.Maid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MaidRepository extends JpaRepository<Maid, UUID>{

}
