package com.iitgn.entryexit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iitgn.entryexit.entities.Security;

import java.util.Optional;

public interface SecurityRepository extends JpaRepository<Security, Long> {
    Optional<Security> findByEmail(String email);
    Boolean existsByEmail(String email);
}
