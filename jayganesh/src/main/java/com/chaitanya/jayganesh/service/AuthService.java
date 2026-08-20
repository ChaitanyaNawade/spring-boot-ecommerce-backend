package com.chaitanya.jayganesh.service;
import com.chaitanya.jayganesh.dto.LoginRequest;
import com.chaitanya.jayganesh.dto.RegisterRequest;
import com.chaitanya.jayganesh.entity.Role;
import com.chaitanya.jayganesh.entity.User;
import com.chaitanya.jayganesh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private PasswordEncoder passwordEncoder;

    public AuthService(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequest request)
    {
       Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

       if(existingUser.isPresent())
       {
           throw  new RuntimeException("User already registered");       }
       else
       {
            User user = new User();

            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setPhone(request.getPhone());
            user.setAddress(request.getAddress());
            user.setRole(Role.CUSTOMER);

            userRepository.save(user);

            return "user registered successfully";
       }
    }

    public String login(LoginRequest loginRequest)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

       return jwtService.generateToken(loginRequest.getEmail());
    }
}
