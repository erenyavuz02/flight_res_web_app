package com.hitit.project.microservices.flight_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hitit.project.microservices.flight_app.entity.Port;



@Repository
public interface PortRepository  extends JpaRepository<Port, Long> {
    
    List<Port> findByCity(String city);

    Port findByCode(String code);
}
