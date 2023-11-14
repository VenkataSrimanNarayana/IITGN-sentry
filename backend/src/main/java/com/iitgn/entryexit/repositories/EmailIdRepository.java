package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmailIdRepository extends JpaRepository<Email, Email> {

}
