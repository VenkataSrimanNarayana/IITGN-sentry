package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Long Id);

    void updateUserById(Long id, User user);

    void changePasswordById(Long id, String password);

    void changeNameById(Long id, String name);

    void deleteUserById(Long id);
}
