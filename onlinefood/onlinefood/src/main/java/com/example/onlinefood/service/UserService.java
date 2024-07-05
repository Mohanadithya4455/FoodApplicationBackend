package com.example.onlinefood.service;

import com.example.onlinefood.model.User;

public interface UserService {

     public User findUserByJwtToken(String jwt);

     public User findUserByEmail(String email);
}
