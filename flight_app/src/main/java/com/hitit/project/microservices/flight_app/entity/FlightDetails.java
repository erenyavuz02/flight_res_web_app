package com.hitit.project.microservices.flight_app.entity;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class FlightDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cabin;
    private Double price;
    private int availableSeats;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    
}