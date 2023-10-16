package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.Role;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.Designation;
import com.iitgn.entryexit.models.LoginDto;
import com.iitgn.entryexit.models.Provider;
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

import java.util.Collections;

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
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String signup(SignUpDto signUpDto) {
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return "Email Already Exists";
        }

        String email = signUpDto.getEmail();
        if(email.contains("@iitgn.ac.in")){
            User user = new User();
            user.setName(signUpDto.getName());
            user.setEmail(signUpDto.getEmail());
            user.setId(signUpDto.getId());
            user.setProviderId(Provider.LOCAL.name());
            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            Role roles = roleRepository.findByName("ROLE_" + Designation.USER.name()).get();
            user.setRoles(Collections.singleton(roles));
            userRepository.save(user);
            return "User registered successfully";
        }else{
            return "Invalid IITGN Account";
        }
        // create user object
    }

    @Override
    public String managerSignUp(SignUpDto signUpDto) {
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return "Email Already Exists";
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setId(signUpDto.getId());
        user.setProviderId(Provider.LOCAL.name());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Role roles = roleRepository.findByName("ROLE_" + Designation.MANAGER.name()).get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);
        return "Manager registered successfully";
    }


}