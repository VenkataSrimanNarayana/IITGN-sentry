package com.iitgn.entryexit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iitgn.entryexit.entities.Record;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
