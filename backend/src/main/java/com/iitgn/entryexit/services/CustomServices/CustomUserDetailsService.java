package com.iitgn.entryexit.services.CustomServices;

import com.iitgn.entryexit.entities.Security;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.repositories.SecurityRepository;
import com.iitgn.entryexit.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(usernameOrEmail);

        if(user.isPresent()){
            return user.get();
        }else{
            Optional<Security> security = securityRepository.findByEmail(usernameOrEmail);
            if(security.isPresent()){
                return security.get();
            }
        }
    throw new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail);
    }
}