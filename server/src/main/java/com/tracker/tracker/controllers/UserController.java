package com.tracker.tracker.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.tracker.models.MyUserDetails;
import com.tracker.tracker.models.User;
import com.tracker.tracker.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager manager;


    @GetMapping("/api/users/me")
    public ResponseEntity<?> getLoggedInInfo(){
        Map<String , String> map = new HashMap<>();
       User user =  ( (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).user;
       map.put("username", user.getUsername());
       map.put("email", user.getEmail());
       map.put("role", user.getRole().toString());
        return ResponseEntity.status(200).body(map);
    }

    @GetMapping("/api/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User[] getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/api/auth/register")
    public HttpStatus registerNewUser(@RequestBody User user ){
        if (userService.addNewUser(user.getUsername() , user.getPassword() , user.getEmail() , user.getRole()) != null){
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_GATEWAY;
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest req) {
        try {
            Authentication auth = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.username,
                    loginRequest.password
                )
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

                req.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());;


            User user = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).user;
             Map<String , String> map = new HashMap<>();

       map.put("username", user.getUsername());
       map.put("email", user.getEmail());
       map.put("role", user.getRole().toString());
            return ResponseEntity.status(200).body(map);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


}
