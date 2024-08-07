package com.hitit.project.microservices.reservation_app.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitit.project.microservices.reservation_app.dto.PassengerRequest;
import com.hitit.project.microservices.reservation_app.entity.Passenger;
import com.hitit.project.microservices.reservation_app.entity.PassengerID;
import com.hitit.project.microservices.reservation_app.entity.Reservation;
import com.hitit.project.microservices.reservation_app.service.APIService.FlightAPIService;
import com.hitit.project.microservices.reservation_app.service.PassengerService;
import com.hitit.project.microservices.reservation_app.service.ReservationService;
import com.hitit.project.microservices.reservation_app.service.ReservedFlightService;


@RestController
@RequestMapping("/api/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;


    @Autowired
    private ReservationService reservationService;


    @Autowired
    private ReservedFlightService reservedFlightService;


    @Autowired
    private FlightAPIService flightAPIService;

    @PostMapping("/create")
    public ResponseEntity<Passenger> savePassenger(
            @RequestBody PassengerRequest passengerRequest) {


        System.out.println(passengerRequest);
       
        try {
            
            
            Reservation reservation = reservationService.findByPNR(passengerRequest.getPnr_code());
            // Save the passenger using your service layer
            Passenger savedPassenger = passengerService.save(passengerRequest, reservation);

            reservedFlightService.getReservedFlightsByPnrCode(reservation.getPNR()).forEach(reservedFlight -> {
                System.out.println("Decreasing seats of Flight id: " + reservedFlight.getFlightId()  + " with Cabin: " + reservedFlight.getCabin());
                flightAPIService.decreaseAvailableSeats(reservedFlight.getFlightId(), reservedFlight.getCabin());
            });

            return ResponseEntity.ok(savedPassenger);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/searchByPNR")
    public ResponseEntity<List<Passenger>> getMethodName(@RequestParam String pnr_code) {

        
        Reservation reservation = reservationService.findByPNR(pnr_code);

        List<Passenger> passengers = passengerService.findByReservation(reservation);

        return ResponseEntity.ok(passengers);
    }



    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePassenger(@RequestParam String pnr_code, @RequestParam String passportNo) {
        try {
            PassengerID passengerID = new PassengerID(pnr_code, passportNo);
            passengerService.deletePassenger(passengerID);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            return ResponseEntity.status(400).body("error");
        } 
        return ResponseEntity.ok().body("deleted");
        
    }
    
    
}
