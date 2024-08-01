package com.hitit.project.microservices.flight_app.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@lombok.Data
@Builder
public class FlightRequest {


    private String departurePort;
    private String arrivalPort;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate departureDate;
    
    private int adults;
    private int children;
    private int infants;

   
    
}