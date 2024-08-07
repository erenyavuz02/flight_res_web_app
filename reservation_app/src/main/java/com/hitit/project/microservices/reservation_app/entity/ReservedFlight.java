package com.hitit.project.microservices.reservation_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(ReservedFlightID.class)
@Transactional
@Getter
@Setter
public class ReservedFlight {


    @Id
    private String PNR;

    @Id
    private Long flightId;

    private String cabin;


    public ReservedFlight() {}

    public ReservedFlight(String PNR, Long flightId, String cabin) {
        this.PNR = PNR;
        this.flightId = flightId;
        this.cabin = cabin;
    }


    

}
