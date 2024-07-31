package com.hitit.project.microservices.flight_app.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitit.project.microservices.flight_app.entity.Port;
import com.hitit.project.microservices.flight_app.service.PortService;


@RestController
@RequestMapping("/api/ports")
public class PortController {
    

    @Autowired
    PortService portService;

    @GetMapping("/get_ports")
    public ResponseEntity<List<Port>> getAllPorts() {
        List<Port> ports = portService.findAll();
        
        return ResponseEntity.ok(ports);
    }


    /*
    @Bean
    public void createPorts() {
        portService.createPort(new Port("Ankara", "TR", "Esenboğa Havalimanı" ,"ANK"));
        portService.createPort(new Port("Istanbul", "TR", "Istanbul Havalimanı" ,"IST"));
        portService.createPort(new Port("Izmir", "TR", "Karşıyaka Havalimanı" ,"IZM"));
    }

     */




}
