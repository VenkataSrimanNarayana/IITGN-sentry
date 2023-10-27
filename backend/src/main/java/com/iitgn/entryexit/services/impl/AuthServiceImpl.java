package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.Role;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.LoginDto;
import com.iitgn.entryexit.models.SignUpDto;
import com.iitgn.entryexit.repositories.RoleRepository;
import com.iitgn.entryexit.repositories.UserRepository;
import com.iitgn.entryexit.security.JwtTokenProvider;
import com.iitgn.entryexit.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String userSignup(SignUpDto signUpDto) {


        // check if email already exists
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return "Email Already Exists";
        }

        // check if email is valid
        if (!signUpDto.getEmail().endsWith("@iitgn.ac.in")) {
            return "Invalid IITGN Account";
        }

        // check if id is valid
        if (signUpDto.getId() < 0 || signUpDto.getId() > 99999999) {
            return "Invalid ID";
        }

        User user = User.builder().id(signUpDto.getId()).name(signUpDto.getName()).email(signUpDto.getEmail()).password(passwordEncoder.encode(signUpDto.getPassword())).userType(signUpDto.getUserType()).build();

        Optional<Role> role = roleRepository.findByName(signUpDto.getRole());

        if (role.isEmpty()) {
            return "Invalid Role";
        }

        user.setRole(role.get());
        userRepository.save(user);
        return "User Registered Successfully";
    }

}