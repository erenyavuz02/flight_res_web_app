package com.hitit.project.microservices.flight_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitit.project.microservices.flight_app.entity.FlightDetails;
import com.hitit.project.microservices.flight_app.repository.FlightDetailsRepository;



@Service
public class FlightDetailsService {
    
    @Autowired
    FlightDetailsRepository flightDetailsRepository;

  
    
    public List<Object[]> getCabinAndPriceAndAvailableSeatsByFlightId(Long flightId) {
        return flightDetailsRepository.getCabinAndPriceAndAvailableSeatsByFlightId(flightId);
    }
    
    public Integer getAvailableSeatsByFlightId(Long flightId) {
        System.out.println("Flight id: " + flightId);
        int total = flightDetailsRepository.getAvailableSeatsByFlightIdAndCabin(flightId, "Economy");
        total += flightDetailsRepository.getAvailableSeatsByFlightIdAndCabin(flightId, "Business");
        total += flightDetailsRepository.getAvailableSeatsByFlightIdAndCabin(flightId, "First Class");
        System.out.println("Available seats: " + total);
        return total;
    }

    public void save(FlightDetails flightDetails) {
        flightDetailsRepository.save(flightDetails);
    }

    public List<FlightDetails> getByFlightId(Long flightId){

        return flightDetailsRepository.getByFlightId(flightId);
    }

    public void decreaseAvailableSeats(Long flightId, String cabin) {
        if (flightId == null || cabin == null) {
            throw new IllegalArgumentException("Flight ID and cabin cannot be null");
        }
        flightDetailsRepository.decreaseAvailableSeats(flightId, cabin);
    }

    
}

