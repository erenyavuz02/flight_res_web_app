package com.hitit.project.microservices.reservation_app.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hitit.project.microservices.reservation_app.entity.Flight;
import com.hitit.project.microservices.reservation_app.entity.Reservation;
import com.hitit.project.microservices.reservation_app.entity.ReservedFlight;
import com.hitit.project.microservices.reservation_app.service.ReservationService;
import com.hitit.project.microservices.reservation_app.service.ReservedFlightService;




@Controller
@RequestMapping("/api/reservedFlight")
public class ReservedFlightController {
    
    @Autowired
    ReservedFlightService reservedFlightService;

    @Autowired
    ReservationService reservationService;



    @PostMapping("/reserveFlight")
    public ResponseEntity<ReservedFlight> reserveFlight(@RequestParam("PNR_code") String PNR, @RequestParam("flightId") Long flightId, @RequestParam("cabin") String cabin) {
        
        System.out.println("PNR: " + PNR + "flightId: " + flightId + "cabin: " + cabin);
        Reservation reservation = reservationService.findByPNR(PNR);
        ReservedFlight reservedFlight
        = reservedFlightService.save(reservation, flightId, cabin);
        

        return reservedFlight != null ? ResponseEntity.ok(reservedFlight) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getReservedFlightsByPnrCode")
    public List<ReservedFlight> getReservedFlightsByPnrCode(@RequestParam("PNR_code") String PNR) {

        return reservedFlightService.getReservedFlightsByPnrCode(PNR);
    }

    @GetMapping("/getFlightsByPnrCode")
    public ResponseEntity<List<Flight>> getFlightsByPnrCode(@RequestParam("PNR_code") String PNR) {

        System.out.println("Flights are called that reserved for PNR: " + PNR);
        List<Flight> flights = reservedFlightService.getFlightsByPnrCode(PNR);
        return ResponseEntity.ok(flights);

    }
}
