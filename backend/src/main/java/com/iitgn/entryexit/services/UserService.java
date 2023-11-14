package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.Email;
import com.iitgn.entryexit.entities.Role;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.requestdto.PendingRequestSelfDto;
import com.iitgn.entryexit.models.requestdto.SignUpDto;

import java.util.List;
import java.util.Optional;


public interface UserService {

    List<User> getAllUsers(int offset, int limit);

    Optional<User> getUserById(Long Id);

    void updateUserById(Long id, User user);

    void changePasswordById(Long id, String password);

    boolean changeRoleById(Long id, String role);

    void deleteUserById(User user);

    void raiseRequest(Long id, PendingRequestSelfDto pendingRequestSelfDto);

    void addEmail(Long id, Email email);

    Role findRoleById(Long id);
}
