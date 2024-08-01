package com.hitit.project.microservices.reservation_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@IdClass(PassengerID.class)
@Table(name = "passengers")
public class Passenger {
    

    @Id
    @ManyToOne
    @JoinColumn(name = "PNR", foreignKey = @ForeignKey(name = "fk_passenger_reservation"))
    private Reservation reservation;

    @Id
    @Column(name = "passportNo")
    private String passportNo;

    private String name;
    private String surname;
    private String nationalityNo;
    private LocalDate birthDate;
    private String telNo;
    private String gender;
    private String email;
    private String pType;

    // Getters and setters
    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passengerNo) {
        this.passportNo = passengerNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNationalityNo() {
        return nationalityNo;
    }

    public void setNationalityNo(String nationalityNo) {
        this.nationalityNo = nationalityNo;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPType() {
        return pType;
    }

    public void setPType(String pType) {
        this.pType = pType;
    }

    @Override
    public String toString() {
        return "Passenger [reservation=" + reservation + ", passportNo=" + passportNo + ", name=" + name + ", surname="
                + surname + ", nationalityNo=" + nationalityNo + ", birthDate=" + birthDate + ", telNo=" + telNo
                + ", gender=" + gender + ", email=" + email + ", pType=" + pType + "]";
    }

    
}