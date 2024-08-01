package com.hitit.project.microservices.reservation_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hitit.project.microservices.reservation_app.entity.Passenger;
import com.hitit.project.microservices.reservation_app.entity.PassengerID;
import com.hitit.project.microservices.reservation_app.entity.Reservation;

@Repository
@Transactional
public interface PassengerRepository  extends JpaRepository<Passenger, PassengerID> {
    

    List<Passenger> findByReservation(Reservation reservation);

    void deleteByReservation(Reservation reservation);


}
