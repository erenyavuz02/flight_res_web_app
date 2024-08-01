package com.hitit.project.microservices.flight_app.service.APIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@Service
public class ReservationAPIService {
    private final String BASE_URL = "${reservation.api.url}";

    @Autowired
    private WebClient.Builder webClientBuilder;



    
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
