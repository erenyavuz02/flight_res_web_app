package com.hitit.project.microservices.flight_app;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hitit.project.microservices.flight_app.service.FlightService;
import com.hitit.project.microservices.flight_app.service.PortService;


@Configuration
public class DataInitializer {

    @Autowired
    private PortService portService;

    @Autowired
    private FlightService flightService;
    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            portService.populatePorts();
            flightService.generateFlightsAndDetails();
        };
    }
}