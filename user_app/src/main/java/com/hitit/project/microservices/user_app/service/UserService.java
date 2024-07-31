package com.hitit.project.microservices.user_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitit.project.microservices.user_app.entity.User;
import com.hitit.project.microservices.user_app.repository.UserRepository;


@Service
public class UserService {


    @Autowired
    UserRepository userRepository;

   
    public User save(User user) {
        return userRepository.save(user);
    }

    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public String deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
        return "deleted";
    }


    
}


