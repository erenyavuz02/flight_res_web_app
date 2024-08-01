package com.hitit.project.microservices.reservation_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(ReservedFlightID.class)
public class ReservedFlight {
    

    @Id
    @ManyToOne
    @JoinColumn(name = "PNR", referencedColumnName = "pnr")
    private Reservation reservation;

    @Id
    @ManyToOne
    @JoinColumn(name = "f_id", referencedColumnName = "f_id")
    private Flight flight;

    private String cabin;


    public ReservedFlight() {}

    public ReservedFlight(Reservation reservation, Flight flight, String cabin) {
        this.reservation = reservation;
        this.flight = flight;
        this.cabin = cabin;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    @Override
    public String toString() {
        return "ReservedFlight [reservation=" + reservation + ", flight=" + flight + ", cabin=" + cabin + "]";
    }


    

}
