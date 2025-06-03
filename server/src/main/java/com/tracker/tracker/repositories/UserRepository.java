package com.tracker.tracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tracker.tracker.models.User;

@Repository
public interface UserRepository extends JpaRepository <User , Integer> {

    @Query("select u from User  u where u.username = :username")
    public User findUserByUsername(String username);

    @Query("select u from User u")
    public User[] findAllUsers();

    

}
