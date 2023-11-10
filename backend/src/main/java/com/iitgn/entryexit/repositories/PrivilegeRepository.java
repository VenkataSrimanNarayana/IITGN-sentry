package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
    Optional<Privilege> findByName(String name);
}
