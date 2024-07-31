package com.hitit.project.microservices.flight_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hitit.project.microservices.flight_app.entity.FlightDetails;

@Repository
public interface FlightDetailsRepository extends JpaRepository<FlightDetails, Long> {


    @Query("SELECT fd FROM FlightDetails fd WHERE fd.flight.id = :flightId")
    List<FlightDetails> getByFlightId(@Param("flightId") Long flightId);
    
    @Query("SELECT fd.cabin, fd.price, fd.availableSeats FROM FlightDetails fd WHERE fd.flight.id = :flightId")
    List<Object[]> getCabinAndPriceAndAvailableSeatsByFlightId(@Param("flightId") Long flightId);
    
    @Query("SELECT fd.availableSeats FROM FlightDetails fd WHERE fd.flight.id = :flightId AND fd.cabin = :cabin")
    Integer getAvailableSeatsByFlightIdAndCabin(@Param("flightId") Long flightId, @Param("cabin") String cabin);

    @Modifying
    @Transactional
    @Query("UPDATE FlightDetails fd SET fd.availableSeats = fd.availableSeats - 1 WHERE fd.flight.id = :flightId AND fd.cabin = :cabin")
    void decreaseAvailableSeats(@Param("flightId") Long flightId, @Param("cabin") String cabin);
    
}


