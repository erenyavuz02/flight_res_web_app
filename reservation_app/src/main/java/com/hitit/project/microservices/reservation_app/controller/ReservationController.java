package com.hitit.project.microservices.reservation_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitit.project.microservices.reservation_app.dto.ReservationRequest;
import com.hitit.project.microservices.reservation_app.entity.Reservation;
import com.hitit.project.microservices.reservation_app.service.APIService.UserAPIService;
import com.hitit.project.microservices.reservation_app.service.ReservationService;



@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;


    @Autowired
    private UserAPIService userApiService;

    @PostMapping("/create_reservation")
    public ResponseEntity<String> createReservation(@RequestBody ReservationRequest reservationRequest) {

        String username  = reservationRequest.username();
        String password = reservationRequest.password();

        userApiService.validateUser(username, password);

        System.out.println("u_id: " + username);
        Reservation reservation = reservationService.createReservation(username);

        return ResponseEntity.ok().body(reservation.getPNR());
    }


    @DeleteMapping("/delete_reservation")
    public ResponseEntity<String> deleteReservation(@RequestParam String pnr_code) {

        System.out.println("pnr: " + pnr_code);
        try {
            reservationService.deleteReservation(pnr_code);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            return ResponseEntity.status(400).body("error");
        } 


        return ResponseEntity.ok().body("deleted");
        
    }

    @PostMapping("/ticket_reservation")
    public ResponseEntity<String> postMethodName(@RequestParam String pnr_code) {

        System.out.println("pnr: " + pnr_code + "ticketed");
        if (reservationService.ticketReservation(pnr_code) == null) {
            return ResponseEntity.status(400).body("error");
        } else {
            return ResponseEntity.ok().body("ticketed");
        }
    }
    
   
    

}

