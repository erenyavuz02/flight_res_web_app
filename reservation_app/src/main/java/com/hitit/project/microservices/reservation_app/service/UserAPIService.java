package com.hitit.project.microservices.reservation_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.hitit.project.microservices.reservation_app.entity.User;
import com.hitit.project.microservices.reservation_app.exception.InvalidUserException;

import reactor.core.publisher.Mono;

@Service
public class UserAPIService {
    
    private final String BASE_URL = "${user.api.url}";

    @Autowired
    private WebClient.Builder webClientBuilder;

    public User findByUsername(String username){
        

        String url = BASE_URL + "get?username=" + username;

         try {
            Mono<User> response = webClientBuilder
                .build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(User.class);

            return response.block();
        } catch (WebClientResponseException ex) {
            throw new InvalidUserException("User not found");
        }


    }
}
