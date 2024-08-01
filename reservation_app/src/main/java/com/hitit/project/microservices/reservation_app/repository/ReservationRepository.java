package com.hitit.project.microservices.reservation_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hitit.project.microservices.reservation_app.entity.Reservation;



@Repository
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    
    /**
     * Finds all reservations with a specific PNR.
     *
     * @param pNR the PNR to search for
     * @return a list of Reservation objects with the specified PNR
     */
    Optional<Reservation> findByPNR(String PNR);
    @Query("SELECT r FROM Reservation r WHERE r.user.u_id = :u_id")
    List<Reservation> findByUserId(Long u_id);

}

