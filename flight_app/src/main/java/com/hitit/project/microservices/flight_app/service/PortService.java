package com.hitit.project.microservices.flight_app.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitit.project.microservices.flight_app.entity.Port;
import com.hitit.project.microservices.flight_app.repository.PortRepository;


@Service
public class PortService {
    

    @Autowired
    PortRepository portRepository;


    public List<Port> findByCity(String city) {
        return portRepository.findByCity(city);
    }
    

    public Port findByCode(String code) {
        return portRepository.findByCode(code);
    }

    public List<Port> findAll() {
        return portRepository.findAll();
    }

    public void createPort(Port port) {
        portRepository.save(port);
    }


    public void populatePorts() {
        List<Port> ports = Arrays.asList(
            new Port( "Istanbul", "Turkey",  "Istanbul Airport","IST"),
            new Port("Ankara", "Turkey", "Esenboga Airport", "ESB"),
            new Port( "Izmir", "Turkey", "Adnan Menderes Airport", "ADB"),
            new Port( "Antalya", "Turkey", "Antalya Airport", "AYT"),
            new Port( "Bodrum", "Turkey", "Milas-Bodrum Airport", "BJV")
        );

        portRepository.saveAll(ports);
    }
    
}
