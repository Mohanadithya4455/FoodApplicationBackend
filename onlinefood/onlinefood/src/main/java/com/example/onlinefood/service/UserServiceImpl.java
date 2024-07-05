package com.example.onlinefood.service;

import com.example.onlinefood.config.JwtTokenProvider;
import com.example.onlinefood.model.User;
import com.example.onlinefood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Override
    public User findUserByJwtToken(String jwt) {
        String email=jwtTokenProvider.getEmailFromToken(jwt);
        User user=findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user=userRepository.findByEmail(email);
        if(user==null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
