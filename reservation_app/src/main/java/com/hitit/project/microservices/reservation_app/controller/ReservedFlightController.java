package com.hitit.project.demo.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hitit.project.demo.entity.Reservation;
import com.hitit.project.demo.entity.ReservedFlight;
import com.hitit.project.demo.service.ReservationService;
import com.hitit.project.demo.service.ReservedFlightService;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/api/reservedFlight")
public class ReservedFlightController {
    
    @Autowired
    ReservedFlightService reservedFlightService;

    @Autowired
    ReservationService reservationService;



    @PostMapping("/reserveFlight")
    public ResponseEntity<ReservedFlight> reserveFlight(@RequestParam("PNR_code") String PNR, @RequestParam("flightId") Long flightId, @RequestParam("cabin") String cabin) {
        //TODO: process POST request
        
        System.out.println("PNR: " + PNR + "flightId: " + flightId + "cabin: " + cabin);
        Reservation reservation = reservationService.findByPNR(PNR);
        ReservedFlight reservedFlight
        = reservedFlightService.save(reservation, flightId, cabin);
        

        return reservedFlight != null ? ResponseEntity.ok(reservedFlight) : ResponseEntity.notFound().build();
    }
}
