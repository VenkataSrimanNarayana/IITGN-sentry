package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.Email;
import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.requestdto.PendingRequestSelfDto;
import com.iitgn.entryexit.repositories.UserRepository;
import com.iitgn.entryexit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


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
    public void updateUserById(Long id, User user) {
        userRepository.save(user);
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

    @Override
    public void raiseRequest(Long id, PendingRequestSelfDto pendingRequestSelfDto) {
        Optional<User> userTemp = userRepository.findById(id);
        if(userTemp.isPresent()){
            User user = userTemp.get();
            PendingRequest pendingRequest = PendingRequest.builder()
                    .validFromDate(pendingRequestSelfDto.getValidFromDate())
                    .validFromTime(pendingRequestSelfDto.getValidFromTime())
                    .validUptoDate(pendingRequestSelfDto.getValidUptoDate())
                    .validUptoTime(pendingRequestSelfDto.getValidUptoTime())
                    .reason(pendingRequestSelfDto.getReason())
                    .build();

            user.getPendingRequest().add(pendingRequest);
            userRepository.save(user);
        }
    }

    @Override
    public void addEmail(Long id, Email emailRequestDto) {
        Optional<User> userTemp = userRepository.findById(id);
        if(userTemp.isPresent()){
            User user = userTemp.get();

            Set<Email> emailList = user.getEmails();
            for(Email email : emailList){
                if(email.getType().equals(emailRequestDto.getType())){
                    email.setEmail(emailRequestDto.getEmail());
                    userRepository.save(user);
                    return;
                }
            }

            Email email = Email.builder().type(emailRequestDto.getType())
                    .email(emailRequestDto.getEmail())
                    .build();
            user.getEmails().add(email);
            userRepository.save(user);
        }
    }
}
