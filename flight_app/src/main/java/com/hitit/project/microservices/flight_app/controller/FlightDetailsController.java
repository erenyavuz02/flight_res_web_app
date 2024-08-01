package com.hitit.project.microservices.flight_app.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitit.project.microservices.flight_app.entity.FlightDetails;
import com.hitit.project.microservices.flight_app.service.FlightDetailsService;




@RestController
@RequestMapping("/api/flightDetails")
public class FlightDetailsController {

    @Autowired
    private FlightDetailsService flightDetailsService;



    @GetMapping("/")
    public List<FlightDetails> getFlightDetails(@RequestParam("flightId") Long flightId) {
        for (FlightDetails fd : flightDetailsService.getByFlightId(flightId)) {
            System.out.println(fd.toString());
        }
        return flightDetailsService.getByFlightId(flightId);
    }

    @PostMapping("/decreaseAvailableSeats")
    public ResponseEntity<String> postMethodName(@RequestParam Long flightId, @RequestParam String cabin) {
        
        try {

            flightDetailsService.decreaseAvailableSeats(flightId, cabin);

            return ResponseEntity.ok().body("updated");
            
        } catch (Exception e) {
            
            return ResponseEntity.status(400).body(e.getMessage());
           
        }
        

    }
    
    
    
    
}

