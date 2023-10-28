package com.iitgn.entryexit.services.CustomServices;

import com.iitgn.entryexit.entities.User;
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

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(Long.valueOf(userId));

        if(user.isPresent()){
            return user.get();
        }
    throw new UsernameNotFoundException("User not found with username or email : " + userId);
    }
}