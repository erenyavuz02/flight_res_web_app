package com.hitit.project.microservices.reservation_app.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(PassengerID.class)
@Table(name = "passengers")
@Data
@Getter
@Setter
public class Passenger {
    

    @Id
    private String PNR;

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


    
}