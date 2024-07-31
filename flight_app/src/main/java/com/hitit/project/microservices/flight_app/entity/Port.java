package com.hitit.project.microservices.flight_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

@Entity
@Table(name = "ports")
@Data
public class Port {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long port_id;

    @NonNull
    @Column(nullable = false)
    private String city;

    @NonNull
    @Column(nullable = false)
    private String country;

    @NonNull
    @Column(nullable = false, unique = true)
    private String code;

    @NonNull
    @Column(nullable = false, unique = true)
    private String portName;

  

}
