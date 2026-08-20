package com.chaitanya.jayganesh.service;

import com.chaitanya.jayganesh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService
{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<com.chaitanya.jayganesh.entity.User> byUsername =  userRepository.findByEmail(username);

        if(byUsername.isPresent())
        {
            return User.builder()
                    .username(byUsername.get().getEmail())
                    .password(byUsername.get().getPassword())
                    .authorities(byUsername.get().getRole().toString())
                    .build();
        }

        throw  new UsernameNotFoundException("user not found with username"+username);
    }
}
