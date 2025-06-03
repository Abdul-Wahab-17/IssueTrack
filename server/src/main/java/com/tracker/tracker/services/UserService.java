package com.tracker.tracker.services;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tracker.tracker.models.MyUserDetails;
import com.tracker.tracker.models.Role;
import com.tracker.tracker.models.User;
import com.tracker.tracker.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        return new MyUserDetails(user);
    }

    public User[] getAllUsers(){
        return userRepository.findAllUsers();
    }

    public Map<String , String> getUserInfo(String username){
        Map<String , String> map = new HashMap<>();
        User user = userRepository.findUserByUsername(username);
        map.put("username", username);
        map.put("role", user.getRole().toString());
        map.put("email", user.getEmail());
        return map;
    }

    public User addNewUser(String username , String password , String email , Role role){

       try {
        User user = new User();
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder(12).encode(password));
        user.setRole(role);
        user.setUsername(username);

        return userRepository.save(user);}
    catch (DataIntegrityViolationException e) {
        return null;
    }
    }

}
