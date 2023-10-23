package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.Role;
import com.iitgn.entryexit.entities.Security;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.models.EnumModels.Designation;
import com.iitgn.entryexit.models.LoginDto;
import com.iitgn.entryexit.models.EnumModels.Provider;
import com.iitgn.entryexit.models.SignUpDto;
import com.iitgn.entryexit.repositories.RoleRepository;
import com.iitgn.entryexit.repositories.SecurityRepository;
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
    private final SecurityRepository securityRepository;
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
        if(email.endsWith("@iitgn.ac.in")){
            User user = User.builder().id(signUpDto.getId()).
                    name(signUpDto.getName()).
                    email(signUpDto.getEmail()).
                    providerId(Provider.LOCAL.name()).
                    password(passwordEncoder.encode(signUpDto.getPassword())).build();

//            Optional<Role> tempRoles = roleRepository.findByName("ROLE_" + Designation.USER.name());
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
    public String securitySignUp(SignUpDto signUpDto) {
        if (securityRepository.existsByEmail(signUpDto.getEmail())) {
            return "Email Already Exists";
        }

        String email = signUpDto.getEmail();
        if (email.endsWith("@iitgn.ac.in")) {
            Security security = Security.builder().id(signUpDto.getId()).
                    name(signUpDto.getName()).
                    email(signUpDto.getEmail()).
                    providerId(Provider.LOCAL.name()).
                    password(passwordEncoder.encode(signUpDto.getPassword())).build();

            Role roles = roleRepository.findByName("ROLE_" + Designation.MANAGER.name()).get();
            security.setRoles(Collections.singleton(roles));
            securityRepository.save(security);
            return "Security registered successfully";
        }else{
            return "Invalid IITGN Account";
        }
    }


}