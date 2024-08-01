package com.hitit.project.microservices.reservation_app.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitit.project.microservices.reservation_app.dto.PassengerRequest;
import com.hitit.project.microservices.reservation_app.entity.Passenger;
import com.hitit.project.microservices.reservation_app.entity.PassengerID;
import com.hitit.project.microservices.reservation_app.entity.Reservation;
import com.hitit.project.microservices.reservation_app.repository.PassengerRepository;





/**
 * This class provides services for passengers.
 * It uses the passenger repository to perform CRUD operations on passengers.
 */
@Service
public class PassengerService {

    /**
     * The passenger repository provides methods to retrieve and store passengers.
     */
    @Autowired
    private PassengerRepository passengerRepository;


    /**
     * Saves a passenger to the repository.
     * @param passenger The passenger to save.
     * @return The saved passenger.
     */

    public Passenger save(Passenger passenger) {    
        return passengerRepository.save(passenger);
    }

    /**
     * Saves a passenger to the repository.
     * @param passengerRequest The passenger request containing passenger details.
     * @param reservation The reservation associated with the passenger.
     * @return The saved passenger.
     */
    public Passenger save(PassengerRequest passengerRequest, Reservation reservation) {

        Passenger passenger = new Passenger();
        passenger.setReservation(reservation);
        passenger.setPassportNo(passengerRequest.getPassportNo());
        passenger.setName(passengerRequest.getName());
        passenger.setSurname(passengerRequest.getSurname());
        passenger.setNationalityNo(passengerRequest.getNationalityNo());
        passenger.setBirthDate(passengerRequest.getBirthDate());
        passenger.setTelNo(passengerRequest.getTelNo());
        passenger.setGender(passengerRequest.getGender());
        passenger.setEmail(passengerRequest.getEmail());
        passenger.setPType(passengerRequest.getpType());
        
        return passengerRepository.save(passenger);
    }



    /**
     * Retrieves passengers by reservation from the repository.
     * @param reservation The reservation to retrieve passengers for.
     * @return A list of passengers associated with the reservation.
     */
    public List<Passenger> findByReservation(Reservation reservation) {


        return passengerRepository.findByReservation(reservation);
    }

    /**
     * Deletes passengers associated with a reservation from the repository.
     * @param reservation The reservation to delete passengers for.
     */
    public void deletePassengersByReservation(Reservation reservation) {

        passengerRepository.deleteByReservation(reservation);
    }

    /**
     * Deletes a passenger from the repository.
     * @param passengerID The ID of the passenger to delete.
     */
    public void deletePassenger(PassengerID passengerID) {
        passengerRepository.deleteById(passengerID);
    }
    
}
