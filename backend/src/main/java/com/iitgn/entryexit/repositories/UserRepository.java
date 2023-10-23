package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);


//    @Transactional
//    @Modifying
//    // write query to update all the details of the user
//    @Query("UPDATE User u SET u.name = ?2, u.email = ?3, u.password = ?4 WHERE u.id = ?1")
//    void updateUserById(Long id, String name,);
//

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = ?2 WHERE u.id = ?1")
    void changePasswordById(Long id, String password);


    // query for updating name by id
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.name = ?2 WHERE u.id = ?1")
    void changeNameById(Long id, String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = ?1")
    void deleteById(Long id);
}

