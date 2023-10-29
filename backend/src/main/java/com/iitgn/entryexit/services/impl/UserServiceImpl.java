package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.SignUpDto;
import com.iitgn.entryexit.repositories.UserRepository;
import com.iitgn.entryexit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public List<User> getAllUsers(int offset, int limit) {
        return userRepository.findAll(PageRequest.of(offset / limit, limit)).getContent();
    }

    @Override
    public Optional<User> getUserById(Long Id) {
        return userRepository.findById(Id);
    }


    // All update methods
    @Override
    public void updateUserById(Long id, SignUpDto signUpDto) {
//        userRepository.updateUserById(id, signUpDto.getFirstName());
    }


    // All change methods
    @Override
    public void changePasswordById(Long id, String password) {
        userRepository.changePasswordById(id, password);
    }

    @Override
    public boolean changeRoleById(Long id, String role) {
        return userRepository.changeRoleById(id, role) >= 1;
    }


    // All delete methods
    @Override
    public void deleteUserById(User user) {
        userRepository.delete(user);
    }
}
