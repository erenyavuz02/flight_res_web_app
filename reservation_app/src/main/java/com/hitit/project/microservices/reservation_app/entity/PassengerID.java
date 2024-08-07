package com.hitit.project.microservices.reservation_app.entity;

import java.io.Serializable;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassengerID implements Serializable {
    private String PNR;

    private String passportNo;

    
}
