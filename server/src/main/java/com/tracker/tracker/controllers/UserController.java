package com.tracker.tracker.controllers;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.tracker.models.MyUserDetails;
import com.tracker.tracker.models.Role;
import com.tracker.tracker.models.User;
import com.tracker.tracker.services.UserService;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager manager;

    @GetMapping("/api/users/me")
    public Map<String , String> getLoggedInInfo(){
        Map<String , String> map = new HashMap<>();
       String username =  ( (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getUsername();
       map = userService.getUserInfo(username);
        return map;

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
    public HttpStatus login(@RequestBody LoginRequest loginRequest){

      try {  Authentication auth = manager.authenticate( new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return HttpStatus.OK;}
    catch (BadCredentialsException e){
        return HttpStatus.UNAUTHORIZED;
    }

}
}
