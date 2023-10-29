package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.SignUpDto;
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
    public void updateUserById(Long id, SignUpDto signUpDto) {
//        userRepository.
    }

    @Override
    public boolean changeRoleById(Long id, String role) {
        return userRepository.changeRoleById(id, role) >= 1;
    }

    @Override
    public void changePasswordById(Long id, String password) {
        userRepository.changePasswordById(id, password);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
