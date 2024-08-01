package com.hitit.project.microservices.reservation_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.hitit.project.microservices.reservation_app.entity.Flight;

import reactor.core.publisher.Mono;

@Service
public class FlightAPIService {
    private final String BASE_URL = "${user.api.url}";

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Flight findById(Long flightId){
        

        String url = BASE_URL + "get?flightId=" + flightId;

         try {
            Mono<Flight> response = webClientBuilder
                .build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(Flight.class);

            return response.block();
        } catch (WebClientResponseException ex) {
            //TODO: create custom exception
            throw new RuntimeException("Flight not found");
        }


    }
}
