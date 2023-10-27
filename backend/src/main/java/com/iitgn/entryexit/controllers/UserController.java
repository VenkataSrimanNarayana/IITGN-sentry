package com.iitgn.entryexit.controllers;


import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.PasswordChangeRequest;
import com.iitgn.entryexit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import  java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

//    @GetMapping("/api/users/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        Optional<User> user = userService.getUserById(id);
//        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

//    @PostMapping("/api/users/{id}")
//    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User user) {
//        return userService.updateUserById(id, user).map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<String> getNameById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Optional<User> user = userService.getUserById(id);
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!currentPrincipalName.equals(user.get().getEmail())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        System.out.println(currentPrincipalName);
        return user.map(value -> new ResponseEntity<>(value.getName(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/api/users/{id}/name")
    public ResponseEntity<User> changeNameById(@PathVariable Long id, @RequestBody String name) {
        userService.changeNameById(id, name);
        return userService.getUserById(id).map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @PutMapping("/api/users/{id}/password")
    public String changePasswordById(@PathVariable Long id, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        // encode the password using bcrypt

        User user = userService.getUserById(id).orElse(null);
        if(user == null){
            return "User not found";
        }

        if(!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), user.getPassword())){
            return "Old password is incorrect";
        }

        if(passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getOldPassword())){
            return "New password cannot be same as old password";
        }

        if (passwordChangeRequest.getNewPassword().length() < 8) {
            return "Password must be at least 8 characters long";
        }

        if (passwordChangeRequest.getNewPassword().length() > 20) {
            return "Password must be at max 20 characters long";
        }

        if (!passwordChangeRequest.getNewPassword().matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter";
        }

        if (!passwordChangeRequest.getNewPassword().matches(".*[a-z].*")) {
            return "Password must contain at least one lowercase letter";
        }

        if (!passwordChangeRequest.getNewPassword().matches(".*[0-9].*")) {
            return "Password must contain at least one digit";
        }

        if (!passwordChangeRequest.getNewPassword().matches(".*[!@#$%^&*].*")) {
            return "Password must contain at least one special character";
        }

        if(passwordChangeRequest.getNewPassword().contains(" ")){
            return "Password cannot contain spaces";
        }

        if (passwordChangeRequest.getNewPassword().contains(passwordChangeRequest.getOldPassword())) {
            return "New password cannot contain old password";
        }

        if (passwordChangeRequest.getNewPassword().contains(user.getName())) {
            return "New password cannot contain name";
        }

        if (passwordChangeRequest.getNewPassword().contains(user.getEmail())) {
            return "New password cannot contain email";
        }

        String encodedPassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
        userService.changePasswordById(id, encodedPassword);
        return "Password changed successfully";
    }


    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}

















