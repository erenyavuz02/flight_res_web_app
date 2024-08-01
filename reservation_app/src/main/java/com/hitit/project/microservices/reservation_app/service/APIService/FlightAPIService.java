package com.hitit.project.microservices.reservation_app.service.APIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    public ResponseEntity<String> decreaseAvailableSeats(Long flightId, String cabin){
        String url = BASE_URL + "decreaseAvailableSeats?flightId=" + flightId + "&cabin=" + cabin;

        try {
            Mono<ResponseEntity<String>> response = webClientBuilder
                .build()
                .post()
                .uri(url)
                .retrieve()
                .toEntity(String.class);

            return response.block();
        } catch (WebClientResponseException ex) {
            //TODO: create custom exception
            throw new RuntimeException("Something went wrong");
        }
    }
    
}
