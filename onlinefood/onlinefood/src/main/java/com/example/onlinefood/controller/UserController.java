package com.example.onlinefood.controller;


import com.example.onlinefood.model.User;
import com.example.onlinefood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/profile")
    public User getUser(@RequestHeader("Authorization") String jwt){
        User user=userService.findUserByJwtToken(jwt);
        return user;
    }
}
