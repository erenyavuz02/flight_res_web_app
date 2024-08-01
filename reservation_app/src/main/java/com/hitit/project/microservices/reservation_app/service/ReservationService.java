package com.hitit.project.microservices.reservation_app.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitit.project.microservices.reservation_app.entity.Reservation;
import com.hitit.project.microservices.reservation_app.entity.User;
import com.hitit.project.microservices.reservation_app.repository.ReservationRepository;
import com.hitit.project.microservices.reservation_app.service.APIService.UserAPIService;


@Service
public class ReservationService {
    

    @Autowired
    private ReservationRepository reservationRepository;

 
    @Autowired
    private UserAPIService userService;



    @Autowired
    private PassengerService passengerService;

    @Autowired
    private ReservedFlightService reservedFlightService;
    

    public Optional<Reservation> save(Reservation reservation) {
        reservationRepository.save(reservation);
        return Optional.of(reservation);
    }

    public List<Reservation> findReservationsByUserId(Long u_id) {
        return reservationRepository.findByUserId(u_id);
    }


    public Reservation findByPNR(String PNR) {
        Optional<Reservation> reservationOptional = reservationRepository.findByPNR(PNR);
        if (reservationOptional.isPresent()) {
            return reservationOptional.get();
        }
        return null;
    }

    public Reservation createReservation( String username) {
        

        User user = userService.findByUsername(username);
        System.out.println(user);
        String pnr = null;
        while (pnr == null) {
            pnr = generatePNR();
            if (findByPNR(pnr) != null) {
                pnr = null;
            }
        }
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setPNR(pnr);
        reservation.setStatus("hold");
        reservationRepository.save(reservation);

        return reservation;
    }




    // Characters to be used in the PNR code
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int PNR_LENGTH = 6;
    private static final Random random = new Random();

    public  String generatePNR() {

        StringBuilder pnrCode = new StringBuilder(PNR_LENGTH);

        
        for (int i = 0; i < PNR_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            pnrCode.append(CHARACTERS.charAt(index));
        }

        return pnrCode.toString();
    }

    public void deleteReservation(String pnr) {
        Reservation reservation = findByPNR(pnr);
        passengerService.deletePassengersByReservation(reservation);
        reservedFlightService.deleteReservedFlightsByReservation(reservation);
        reservationRepository.delete(reservation);
    }

    public Reservation ticketReservation(String pnr_code) {

        Reservation reservation = findByPNR(pnr_code);
        reservation.setStatus("ticketed");
        reservationRepository.save(reservation);

        return reservation;
    }



}
