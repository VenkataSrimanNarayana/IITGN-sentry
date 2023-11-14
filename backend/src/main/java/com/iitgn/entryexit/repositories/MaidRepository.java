package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.Maid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MaidRepository extends JpaRepository<Maid, UUID>{

    @Query("SELECT m FROM Maid m WHERE m.user.id = ?1")
    List<Maid> findMaidByUserId(Long id);
}
