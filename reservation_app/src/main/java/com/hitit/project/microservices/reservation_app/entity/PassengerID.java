package com.hitit.project.microservices.reservation_app.entity;

import java.io.Serializable;
import java.util.Objects;

public class PassengerID implements Serializable {
    private Reservation reservation;

    private String passportNo;


    

    public PassengerID() {
    }
    
    public PassengerID(Reservation reservation, String passportNo) {
        this.reservation = reservation;
        this.passportNo = passportNo;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservation,passportNo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PassengerID other = (PassengerID) obj;
        if (reservation == null) {
            if (other.reservation != null)
                return false;
        } else if (!reservation.equals(other.reservation))
            return false;
        if (passportNo == null) {
            if (other.passportNo != null)
                return false;
        } else if (!passportNo.equals(other.passportNo))
            return false;
        return true;
    }

    
}
