package com.hitit.project.microservices.reservation_app.service.APIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.hitit.project.microservices.reservation_app.entity.Flight;

import reactor.core.publisher.Mono;

@Service
public class FlightAPIService {
    private String host;
    private String port;
    private String baseUrl;

    

    @Autowired
    private WebClient.Builder webClientBuilder;


    @Autowired
    public FlightAPIService(Environment env) {
        host = env.getProperty("flight.api.host");
        port = env.getProperty("flight.api.port");
        this.baseUrl = "http://" + host + ":" + port ;
    }

    public Flight findById(Long flightId){


        String url = baseUrl + "/api/flights/" +  "searchByFlightId?flightId=" + flightId;

        System.out.println(url);

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
            throw new RuntimeException("Flight not found " + ex.getMessage());
        }


    }


    public ResponseEntity<String> decreaseAvailableSeats(Long flightId, String cabin) {
        String url = baseUrl +"/api/flightDetails/" +  "decreaseAvailableSeats?flightId=" + flightId + "&cabin=" + cabin;

        System.out.println(url);

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
            throw new RuntimeException( "Something went wrong: " + ex.getMessage() );
        }
    }
    
}