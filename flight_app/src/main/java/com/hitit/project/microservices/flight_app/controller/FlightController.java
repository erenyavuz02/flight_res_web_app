package com.hitit.project.microservices.flight_app.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitit.project.microservices.flight_app.dto.FlightRequest;
import com.hitit.project.microservices.flight_app.entity.Flight;
import com.hitit.project.microservices.flight_app.service.FlightService;





@RestController
@RequestMapping("/api/flights")
public class FlightController {
    
    @Autowired
    private FlightService flightService;
    

   

    @PostMapping("/search")
    public List<Flight> searchFlights(@RequestBody FlightRequest flightRequest) {
        return flightService.searchFlights(
                flightRequest.getDeparturePort(),
                flightRequest.getArrivalPort(),
                flightRequest.getDepartureDate(),
                flightRequest.getAdults(),
                flightRequest.getChildren(),
                flightRequest.getInfants());
    }
    //TODO: create FlightRequest file


    /**
     * Get flight by id
     * This method cannot be called by gateway, only for other microservices
     * 
     * @param flightId
     * @return
     */
    @GetMapping("/searchByFlightId")
    public ResponseEntity<Flight> getFlightById(@RequestParam Long flightId) {
        Flight flight = flightService.findById(flightId);
        if (flight == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(flight);
    }
    

}

