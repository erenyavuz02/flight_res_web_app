package com.hitit.project.microservices.reservation_app.service.APIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.hitit.project.microservices.reservation_app.dto.UserDetails;
import com.hitit.project.microservices.reservation_app.entity.User;
import com.hitit.project.microservices.reservation_app.exception.InvalidUserException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserAPIService {
    
    @Value("${user.api.url}")
    private String baseUrl;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public User findByUsername(String username){
        

        String url = baseUrl + "get?username=" + username;

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


    

    public UserDetails validateUser(String username, String password){
        
        log.debug("Validating user with username: {} and password: {}", username, password);

        String url = baseUrl + "validate?username=" + username + "&password=" + password;

        System.out.println(url);
        
         try {
            Mono<UserDetails> response = webClientBuilder
                .build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(UserDetails.class);

            UserDetails userDetails = response.block();
            log.debug("User details received: {}", userDetails);
            return userDetails;
        } catch (WebClientResponseException ex) {
            log.error("WebClientResponseException occurred while validating user: {}", ex.getMessage());
            throw new InvalidUserException("User not found");
        }


    }
}
