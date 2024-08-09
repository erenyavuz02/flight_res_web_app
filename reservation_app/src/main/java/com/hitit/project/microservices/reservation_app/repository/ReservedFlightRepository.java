package com.hitit.project.microservices.reservation_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hitit.project.microservices.reservation_app.entity.ReservedFlight;
import com.hitit.project.microservices.reservation_app.entity.ReservedFlightID;



@Repository
@Transactional
public interface ReservedFlightRepository extends JpaRepository<ReservedFlight, ReservedFlightID> {


    @Query(value = "SELECT flight_id FROM reserved_flight WHERE pnr = ?1", nativeQuery = true)
    List<Long> getFlightIdsByPnrCode(String pnr_code);
 

    @Query(value = "SELECT * FROM reserved_flight WHERE pnr = ?1", nativeQuery = true)
    List<ReservedFlight> getReservedFlightsByPnrCode(String pnr);


    void deleteByPNR(String pnr);
    
}
