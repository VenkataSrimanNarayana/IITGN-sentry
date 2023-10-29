package com.iitgn.entryexit.controllers;


import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.PasswordChangeRequest;
import com.iitgn.entryexit.models.SignUpDto;
import com.iitgn.entryexit.models.SingleLineResponse;
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

    @PreAuthorize("hasAuthority('READ_USERS_PRIVILEGE')")
    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_SINGLE_USER_PRIVILEGE')")
    @GetMapping("/api/users/{id}/details")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PreAuthorize("hasAuthority('READ_SELF_PRIVILEGE')")
    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getSelfById(@PathVariable Long id) {
        // get the current logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if(!currentPrincipalName.equals(id.toString())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAuthority('CHANGE_PASSWORD_PRIVILEGE')")
    @PutMapping("/api/users/{id}/password")
    public ResponseEntity<SingleLineResponse> changePasswordById(@PathVariable Long id, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        // encode the password using bcrypt

        User user = userService.getUserById(id).orElse(null);
        if(user == null){
            return new ResponseEntity<>(new SingleLineResponse("User not found"), HttpStatus.NOT_FOUND);
        }

//        Role role = user.getRole();
//        if(role.getName().equals("ROLE_ADMIN")){
//            return new ResponseEntity<>(new SingleLineResponse("Admin password cannot be changed"), HttpStatus.BAD_REQUEST);
//        }

        if(!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), user.getPassword())){
            return new ResponseEntity<>(new SingleLineResponse("Old password is incorrect"), HttpStatus.BAD_REQUEST);
        }

        if(passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getOldPassword())){
            return new ResponseEntity<>(new SingleLineResponse("New password cannot be same as old password"), HttpStatus.BAD_REQUEST);
        }

        if (passwordChangeRequest.getNewPassword().length() < 8) {
            return new ResponseEntity<>(new SingleLineResponse("Password must be at least 8 characters long"), HttpStatus.BAD_REQUEST);
        }

        if (passwordChangeRequest.getNewPassword().length() > 20) {
            return new ResponseEntity<>(new SingleLineResponse("Password must be at most 20 characters long"), HttpStatus.BAD_REQUEST);
        }

        if (!passwordChangeRequest.getNewPassword().matches(".*[A-Z].*")) {
            return new ResponseEntity<>(new SingleLineResponse("Password must contain at least one uppercase letter"), HttpStatus.BAD_REQUEST);
        }

        if (!passwordChangeRequest.getNewPassword().matches(".*[a-z].*")) {
            return new ResponseEntity<>(new SingleLineResponse("Password must contain at least one lowercase letter"), HttpStatus.BAD_REQUEST);
        }

        if (!passwordChangeRequest.getNewPassword().matches(".*[0-9].*")) {
            return new ResponseEntity<>(new SingleLineResponse("Password must contain at least one digit"), HttpStatus.BAD_REQUEST);
        }

        if (!passwordChangeRequest.getNewPassword().matches(".*[!@#$%^&*].*")) {
            return new ResponseEntity<>(new SingleLineResponse("Password must contain at least one special character"), HttpStatus.BAD_REQUEST);
        }

        if(passwordChangeRequest.getNewPassword().contains(" ")){
            return new ResponseEntity<>(new SingleLineResponse("Password cannot contain spaces"), HttpStatus.BAD_REQUEST);
        }

        if (passwordChangeRequest.getNewPassword().contains(passwordChangeRequest.getOldPassword())) {
            return new ResponseEntity<>(new SingleLineResponse("New password cannot contain old password"), HttpStatus.BAD_REQUEST);
        }

        if (passwordChangeRequest.getNewPassword().contains(user.getFirstName())) {
            return new ResponseEntity<>(new SingleLineResponse("New password cannot contain first name"), HttpStatus.BAD_REQUEST);
        }

        if(passwordChangeRequest.getNewPassword().contains(user.getLastName())){
            return new ResponseEntity<>(new SingleLineResponse("New password cannot contain last name"), HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
        userService.changePasswordById(id, encodedPassword);
        return new ResponseEntity<>(new SingleLineResponse("Password changed successfully"), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('DELETE_USER_PRIVILEGE')")
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User successfully deleted.", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('UPDATE_USER_PRIVILEGE')")
    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody SignUpDto signUpDto) {
        Optional<User> userTemp = userService.getUserById(id);
        if(userTemp.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.updateUserById(id, signUpDto);
        return new ResponseEntity<>(userTemp.get(), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_UPDATE_PRIVILEGE')")
    @PutMapping("/api/users/{id}/role")
    public ResponseEntity<SingleLineResponse> updateUserRoleById(@PathVariable Long id, @RequestBody String role) {

        Optional<User> userTemp = userService.getUserById(id);
        if(userTemp.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(userService.changeRoleById(id, role)){
            return new ResponseEntity<>(new SingleLineResponse("Role changed successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

//    @PreAuthorize("hasAuthority('UPDATE_USER_PRIVILEGE')")
//    @PutMapping("/api/users/{id}/role")
//    public ResponseEntity<User> updateUserRoleById(@PathVariable Long id, @RequestBody Role role) {
//        Optional<User> userTemp = userService.getUserById(id);
//        if(userTemp.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        userService.updateUserRoleById(id, role);
//        return new ResponseEntity<>(userTemp.get(), HttpStatus.OK);
//    }
}

















