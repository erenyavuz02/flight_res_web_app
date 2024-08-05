package com.hitit.project.microservices.reservation_app.dto;

import lombok.Data;


public record ReservationRequest(

    String username,
    String password
) {


    
}
