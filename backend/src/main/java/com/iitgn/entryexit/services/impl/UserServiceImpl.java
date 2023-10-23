package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.repositories.UserRepository;
import com.iitgn.entryexit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long Id) {
        return userRepository.findById(Id);
    }

    @Override
    public void updateUserById(Long id, User user) {
//        userRepository.
    }

    @Override
    public void changePasswordById(Long id, String password) {
        userRepository.changePasswordById(id, password);
    }

    @Override
    public void changeNameById(Long id, String name) {
        userRepository.changeNameById(id, name);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
