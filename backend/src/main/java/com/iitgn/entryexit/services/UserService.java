package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.Role;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.SignUpDto;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Long Id);

    void updateUserById(Long id, SignUpDto signUpDto);

    void changePasswordById(Long id, String password);

    void changeRoleById(Long id, Role role);

    void deleteUserById(Long id);
}
