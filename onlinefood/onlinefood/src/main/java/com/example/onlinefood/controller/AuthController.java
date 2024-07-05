package com.example.onlinefood.controller;


import com.example.onlinefood.config.JwtTokenProvider;
import com.example.onlinefood.model.Cart;
import com.example.onlinefood.model.USER_ROLE;
import com.example.onlinefood.model.User;
import com.example.onlinefood.repository.CartRepository;
import com.example.onlinefood.repository.UserRepository;
import com.example.onlinefood.request.LogIn;
import com.example.onlinefood.response.AuthResponse;
import com.example.onlinefood.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserRepository userRepository;
  @Autowired
    private PasswordEncoder passwordEncoder;
   @Autowired
    private JwtTokenProvider jwtTokenProvider;
  @Autowired
    private CartRepository cartRepository;
   @Autowired
   private CustomerService customerService;
   @PostMapping("/signup")
    private ResponseEntity<AuthResponse> createUser(@RequestBody User user) throws Exception {
        User isExist=userRepository.findByEmail(user.getEmail());
        if(isExist!=null){
            throw new Exception("User already Exists");
        }
        User newUser=new User();
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        userRepository.save(newUser);
        Cart cart=new Cart();
        cart.setCustomer(newUser);
        cartRepository.save(cart);
        AuthResponse authResponse=new AuthResponse();

        Authentication auth=new UsernamePasswordAuthenticationToken(newUser.getEmail(),newUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt=jwtTokenProvider.generateToken(auth);
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully");
        authResponse.setRole(newUser.getRole());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LogIn login) throws Exception {
        String username= login.getEmail();
        String password=login.getPassword();
        Authentication authentication=createAuth(username,password);
        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
        String jwt=jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Logged in successfully");
        authResponse.setRole(USER_ROLE.valueOf(role));
        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }
    public Authentication createAuth(String username,String password) throws Exception{
        UserDetails userDetails=customerService.loadUserByUsername(username);
        if(userDetails==null){
            throw new UsernameNotFoundException("No user with this email");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Credentials");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
