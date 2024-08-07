package com.hitit.project.microservices.reservation_app.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {
    

    @Id
    private String PNR;

    private String status;
    
    private Long u_id;

    
}
