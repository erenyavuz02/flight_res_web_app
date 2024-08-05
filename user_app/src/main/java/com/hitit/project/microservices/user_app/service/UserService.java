package com.hitit.project.microservices.user_app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitit.project.microservices.user_app.dto.UserDetails;
import com.hitit.project.microservices.user_app.entity.User;
import com.hitit.project.microservices.user_app.exception.InvalidUserException;
import com.hitit.project.microservices.user_app.repository.UserRepository;


@Service
public class UserService {


    @Autowired
    UserRepository userRepository;

   
    public User save(User user) {
        return userRepository.save(user);
    }

    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public String deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
        return "deleted";
    }


     public UserDetails getUserDetails(String username, String password) {
        Optional<User> userEntity = userRepository.findByUsername(username);

        if (!userEntity.isPresent()) {
            throw new InvalidUserException("User not found");
        }

        //TODO: add more information to the user entity
        UserDetails userDetails = UserDetails.builder()
            .name(userEntity.get().getName())
            .surname(userEntity.get().getSurname())
            .email(userEntity.get().getEmail())
            .build();


        return userDetails;

    }
    
}


