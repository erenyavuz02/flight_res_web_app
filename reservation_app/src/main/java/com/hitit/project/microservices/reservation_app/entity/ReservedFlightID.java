package com.hitit.project.microservices.reservation_app.entity;

import java.io.Serializable;

public class ReservedFlightID implements Serializable{


    private Reservation reservation;

    private Flight flight;

 

    @Override
    public String toString() {
        return "ReservationFlightID [reservation=" + reservation + ", flight=" + flight + "]";
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((reservation == null) ? 0 : reservation.hashCode());
        result = prime * result + ((flight == null) ? 0 : flight.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReservedFlightID other = (ReservedFlightID) obj;
        if (reservation == null) {
            if (other.reservation != null)
                return false;
        } else if (!reservation.equals(other.reservation))
            return false;
        if (flight == null) {
            if (other.flight != null)
                return false;
        } else if (!flight.equals(other.flight))
            return false;
        return true;
    }
    

    

}
