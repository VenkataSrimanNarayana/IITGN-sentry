package com.iitgn.entryexit.controllers;


import com.iitgn.entryexit.entities.ContactNumber;
import com.iitgn.entryexit.entities.Email;
import com.iitgn.entryexit.entities.Role;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.requestdto.NewRoleDto;
import com.iitgn.entryexit.models.requestdto.PasswordChangeRequestDto;
import com.iitgn.entryexit.models.requestdto.UserDetailsDto;
import com.iitgn.entryexit.models.responses.RoleResponse;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.ContactNumberService;
import com.iitgn.entryexit.services.EmailIdService;
import com.iitgn.entryexit.services.EmailService;
import com.iitgn.entryexit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SimpleMailMessage simpleMailMessage;
    private final EmailService emailService;
    private final EmailIdService emailIdService;
    private final ContactNumberService contactNumberService;

    public Long getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(auth.getName());
    }


    @PreAuthorize("hasAuthority('READ_ACCOUNT_PRIVILEGE')")
    @GetMapping("/api/users/all")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam int offset, @RequestParam int limit) {
        List<User> users = userService.getAllUsers(offset, limit);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('READ_ACCOUNT_PRIVILEGE')")
    @GetMapping("/api/users/{id}/details")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PreAuthorize("hasAuthority('RESET_PASSWORD_PRIVILEGE')")
    @GetMapping("/api/users/reset-password/{id}")
    public ResponseEntity<SingleLineResponse> resetPasswordById(@PathVariable Long id) {
        User user = userService.getUserById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new SingleLineResponse("User not found"), HttpStatus.NOT_FOUND);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if (!currentPrincipalName.equals(id.toString())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        String newPassword = emailService.generateCommonLangPassword();
        simpleMailMessage.setSubject("Password Reset");
        simpleMailMessage.setText("Your new password is " + newPassword);
        String encodedPassword = passwordEncoder.encode(newPassword);
        userService.changePasswordById(id, encodedPassword);
        String text = String.format(Objects.requireNonNull(simpleMailMessage.getText()));

        Set<Email> emails = user.getEmails();
        String email = emails.stream().filter(e -> e.getType().equals("college")).toList().get(0).getEmail();
        emailService.sendEmail(email, "your new password", text);
        return new ResponseEntity<>(new SingleLineResponse("Password reset successfully"), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('READ_USER_ACCOUNT_PRIVILEGE')")
    @GetMapping("/api/users")
    public ResponseEntity<User> getSelfById() {
        Long id = getCurrentUser();
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAuthority('CHANGE_USER_PASSWORD_PRIVILEGE')")
    @PutMapping("/api/users/password")
    public ResponseEntity<SingleLineResponse> changePasswordById(@RequestBody PasswordChangeRequestDto passwordChangeRequestDto) {
        // encode the password using bcrypt
        Long id = getCurrentUser();
        User user = userService.getUserById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new SingleLineResponse("User not found"), HttpStatus.NOT_FOUND);
        }

        if (!passwordEncoder.matches(passwordChangeRequestDto.getOldPassword(), user.getPassword())) {
            return new ResponseEntity<>(new SingleLineResponse("Old password is incorrect"), HttpStatus.BAD_REQUEST);
        }

        if (passwordChangeRequestDto.getNewPassword().equals(passwordChangeRequestDto.getOldPassword())) {
            return new ResponseEntity<>(new SingleLineResponse("New password cannot be same as old password"), HttpStatus.BAD_REQUEST);
        }

        if (passwordChangeRequestDto.getNewPassword().length() < 8) {
            return new ResponseEntity<>(new SingleLineResponse("Password must be at least 8 characters long"), HttpStatus.BAD_REQUEST);
        }

        if (passwordChangeRequestDto.getNewPassword().length() > 20) {
            return new ResponseEntity<>(new SingleLineResponse("Password must be at most 20 characters long"), HttpStatus.BAD_REQUEST);
        }

        if (!passwordChangeRequestDto.getNewPassword().matches(".*[A-Z].*")) {
            return new ResponseEntity<>(new SingleLineResponse("Password must contain at least one uppercase letter"), HttpStatus.BAD_REQUEST);
        }

        if (!passwordChangeRequestDto.getNewPassword().matches(".*[a-z].*")) {
            return new ResponseEntity<>(new SingleLineResponse("Password must contain at least one lowercase letter"), HttpStatus.BAD_REQUEST);
        }

        if (!passwordChangeRequestDto.getNewPassword().matches(".*[0-9].*")) {
            return new ResponseEntity<>(new SingleLineResponse("Password must contain at least one digit"), HttpStatus.BAD_REQUEST);
        }

        if (!passwordChangeRequestDto.getNewPassword().matches(".*[!@#$%^&*].*")) {
            return new ResponseEntity<>(new SingleLineResponse("Password must contain at least one special character"), HttpStatus.BAD_REQUEST);
        }

        if (passwordChangeRequestDto.getNewPassword().contains(" ")) {
            return new ResponseEntity<>(new SingleLineResponse("Password cannot contain spaces"), HttpStatus.BAD_REQUEST);
        }

        if (passwordChangeRequestDto.getNewPassword().contains(passwordChangeRequestDto.getOldPassword())) {
            return new ResponseEntity<>(new SingleLineResponse("New password cannot contain old password"), HttpStatus.BAD_REQUEST);
        }

        if (passwordChangeRequestDto.getNewPassword().contains(user.getFirstName())) {
            return new ResponseEntity<>(new SingleLineResponse("New password cannot contain first name"), HttpStatus.BAD_REQUEST);
        }

        if (passwordChangeRequestDto.getNewPassword().contains(user.getLastName())) {
            return new ResponseEntity<>(new SingleLineResponse("New password cannot contain last name"), HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(passwordChangeRequestDto.getNewPassword());
        userService.changePasswordById(id, encodedPassword);
        return new ResponseEntity<>(new SingleLineResponse("Password changed successfully"), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('DELETE_ACCOUNT_PRIVILEGE')")
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<SingleLineResponse> deleteUserById(@PathVariable Long id) {
        User user = userService.getUserById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new SingleLineResponse("User not found"), HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(user);
        return new ResponseEntity<>(new SingleLineResponse("User deleted successfully"), HttpStatus.OK);
    }


    private User changeUser(Long id, UserDetailsDto userDetailsDto) {
        Optional<User> userTemp = userService.getUserById(id);

        if (userTemp.isEmpty()) {
            return null;
        }

        User user = userTemp.get();
        user.setHouseNo(userDetailsDto.getHouseNo());
        user.setArea(userDetailsDto.getArea());
        user.setLandmark(userDetailsDto.getLandmark());
        user.setPinCode(userDetailsDto.getPinCode());
        user.setTownCity(userDetailsDto.getTownCity());
        user.setState(userDetailsDto.getState());
        user.setCountry(userDetailsDto.getCountry());

        for (Email email :
                userDetailsDto.getEmails()) {
            if(email.getEmail() == null || email.getEmail().isEmpty()) {
                continue;
            }
            emailIdService.saveEmailId(email);
            user.getEmails().add(email);
        }
        for (ContactNumber contactNumber :
                userDetailsDto.getContactNumbers()) {
            if(contactNumber.getPhone() == null || contactNumber.getPhone().isEmpty()) {
                continue;
            }
            contactNumberService.saveContactNumber(contactNumber);
            user.getContactNumbers().add(contactNumber);
        }

        userService.updateUserById(id, user);
        return user;
    }

    private ResponseEntity<SingleLineResponse> checkValidity(UserDetailsDto userDetailsDto) {
        if(userDetailsDto == null) {
            return new ResponseEntity<>(new SingleLineResponse("User details cannot be empty"), HttpStatus.BAD_REQUEST);
        }

        if(userDetailsDto.getHouseNo() == null || userDetailsDto.getHouseNo().isEmpty()) {
            return new ResponseEntity<>(new SingleLineResponse("House No. cannot be empty"), HttpStatus.BAD_REQUEST);
        }
        if(userDetailsDto.getArea() == null || userDetailsDto.getArea().isEmpty()) {
            return new ResponseEntity<>(new SingleLineResponse("Area cannot be empty"), HttpStatus.BAD_REQUEST);
        }

        if(userDetailsDto.getLandmark() == null || userDetailsDto.getLandmark().isEmpty()) {
            return new ResponseEntity<>(new SingleLineResponse("Landmark cannot be empty"), HttpStatus.BAD_REQUEST);
        }

        if(userDetailsDto.getPinCode() == 0) {
            return new ResponseEntity<>(new SingleLineResponse("Pin code cannot be empty"), HttpStatus.BAD_REQUEST);
        }

        if(userDetailsDto.getTownCity() == null || userDetailsDto.getTownCity().isEmpty()) {
            return new ResponseEntity<>(new SingleLineResponse("Town/City cannot be empty"), HttpStatus.BAD_REQUEST);
        }

        if(userDetailsDto.getState() == null || userDetailsDto.getState().isEmpty()) {
            return new ResponseEntity<>(new SingleLineResponse("State cannot be empty"), HttpStatus.BAD_REQUEST);
        }

        if(userDetailsDto.getCountry() == null || userDetailsDto.getCountry().isEmpty()) {
            return new ResponseEntity<>(new SingleLineResponse("Country cannot be empty"), HttpStatus.BAD_REQUEST);
        }

        if(userDetailsDto.getEmails() == null || userDetailsDto.getEmails().isEmpty()) {
            return new ResponseEntity<>(new SingleLineResponse("Emails cannot be empty"), HttpStatus.BAD_REQUEST);
        }
        return null;
    }


    @PreAuthorize("hasAuthority('UPDATE_USER_PRIVILEGE')")
    @PutMapping("/api/users/{id}")
    public ResponseEntity<SingleLineResponse> updateUserById(@PathVariable Long id, @RequestBody UserDetailsDto userDetailsDto) {
        if (changeUser(id, userDetailsDto) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(checkValidity(userDetailsDto) != null){
            return checkValidity(userDetailsDto);
        }
        return new ResponseEntity<>(new SingleLineResponse("User updated successfully"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE_USER_USER_PRIVILEGE')")
    @PutMapping("/api/users")
    public ResponseEntity<SingleLineResponse> updateUserById(@RequestBody UserDetailsDto userDetailsDto) {
        Long id = getCurrentUser();
        if (changeUser(id, userDetailsDto) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new SingleLineResponse("User updated successfully"), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_UPDATE_PRIVILEGE')")
    @PutMapping("/api/users/{id}/role")
    public ResponseEntity<SingleLineResponse> updateUserRoleById(@PathVariable Long id, @RequestBody NewRoleDto newRoleDto) {

        Optional<User> userTemp = userService.getUserById(id);
        if (userTemp.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (userService.changeRoleById(id, newRoleDto.getNewRole())) {
            return new ResponseEntity<>(new SingleLineResponse("Role changed successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PreAuthorize("hasAuthority('ROLE_UPDATE_PRIVILEGE')")
    @GetMapping("/api/users/{id}/role")
    public ResponseEntity<RoleResponse> getRoleByUserId(@PathVariable Long id){
        Role role = userService.findRoleById(id);
        if(role == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(RoleResponse.builder().id(role.getId()).role(role.getName()).build(), HttpStatus.OK);
    }
}













