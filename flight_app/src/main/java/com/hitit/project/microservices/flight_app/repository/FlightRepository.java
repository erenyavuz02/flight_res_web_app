package com.hitit.project.microservices.flight_app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hitit.project.microservices.flight_app.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE f.departurePort.id = :departurePortId AND f.arrivalPort.id = :arrivalPortId AND date(f.date) = :date")
    List<Flight> findFlightsByDateAndPorts(
            @Param("departurePortId") Long departurePortId,
            @Param("arrivalPortId") Long arrivalPortId,
            @Param("date") LocalDate date);

    

}