package com.hitit.project.microservices.reservation_app.entity;

import java.io.Serializable;

import lombok.Data;

@Data

public class ReservedFlightID implements Serializable{


    private String PNR;

    private Long flightId;

}
