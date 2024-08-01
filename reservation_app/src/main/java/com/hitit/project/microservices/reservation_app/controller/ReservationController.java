package com.hitit.project.microservices.reservation_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitit.project.microservices.reservation_app.entity.Reservation;
import com.hitit.project.microservices.reservation_app.service.ReservationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;



@PostMapping("/create_reservation")
    public ResponseEntity<String> createReservation(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String username  = (String)session.getAttribute("user");

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

