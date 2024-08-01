package com.hitit.project.microservices.reservation_app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitit.project.microservices.reservation_app.entity.Flight;
import com.hitit.project.microservices.reservation_app.entity.Reservation;
import com.hitit.project.microservices.reservation_app.entity.ReservedFlight;
import com.hitit.project.microservices.reservation_app.repository.ReservedFlightRepository;
import com.hitit.project.microservices.reservation_app.service.APIService.FlightAPIService;

@Service
public class ReservedFlightService {
    
    @Autowired
    ReservedFlightRepository  reservedFlightRepository;



    @Autowired
    FlightAPIService flightAPIService;


    public void save(ReservedFlight reservedFlight) {
        reservedFlightRepository.save(reservedFlight);
    }


    public ReservedFlight save(Reservation reservation , Long flightId, String cabin) {
        if ( flightId == null || cabin == null) {
            throw new IllegalArgumentException("Parameter cannot be null");
        }
        Flight flight = flightAPIService.findById(flightId);
        if (flight == null) {
            throw new IllegalArgumentException("Flight not found");
        }
        
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }
        ReservedFlight reservedFlight = new ReservedFlight(reservation, flight, cabin);
        try {
            reservedFlightRepository.save(reservedFlight);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save ReservedFlight", e);
        }
        return reservedFlight;
    }


    public List<Flight> getFlightsByPnrCode(String pnr_code) {

        List<Long> flightIds = reservedFlightRepository.getFlightIdsByPnrCode(pnr_code);
        List<Flight> flights = new ArrayList<>();
        for (Long flightId : flightIds) {
            Flight flight = flightAPIService.findById(flightId);
            flights.add(flight);
        }
        return flights;
    }


    public void deleteReservedFlightsByReservation(Reservation reservation) {

        reservedFlightRepository.deleteByReservation(reservation);
    }




    public List<ReservedFlight> getReservedFlightsByPnrCode(String pnr) {

        return reservedFlightRepository.getReservedFlightsByPnrCode(pnr);
    }


    

}
