package com.iitgn.entryexit.repositories;

import com.iitgn.entryexit.entities.Role;
import com.iitgn.entryexit.entities.User;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
//    @Transactional
//    @Modifying
//    // write query to update all the details of the user
//    @Query("UPDATE User u SET u.name = ?2, u.email = ?3, u.password = ?4 WHERE u.id = ?1")
//    void updateUserById(Long id, String name,);
//

//    @Transactional
//    @Modifying
//    @Query("UPDATE User u SET u.firstName = ?2 WHERE u.id = ?1")
//    void updateUserById(Long id, String name);


    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.role = (SELECT r FROM Role r WHERE r.name = :roleName) WHERE u.id = :id")
//    @Query("UPDATE User u SET u.role = ?2 WHERE u.id = ?1")
    int changeRoleById(Long id, String roleName);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = ?2 WHERE u.id = ?1")
    void changePasswordById(Long id, String password);


}

