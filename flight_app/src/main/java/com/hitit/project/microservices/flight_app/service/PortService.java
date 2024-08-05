package com.hitit.project.microservices.flight_app.service;

import java.util.ArrayList;
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

        Port port = new Port();
        List<Port> ports = new ArrayList<>();

        port.setCity("Istanbul");
        port.setCountry("Turkey");
        port.setPortName("Istanbul Airport");
        port.setCode("IST");
        ports.add(port);

        port = new Port();
        port.setCity("Ankara");
        port.setCountry("Turkey");
        port.setPortName("Esenboga Airport");
        port.setCode("ESB");
        ports.add(port);

        port = new Port();
        port.setCity("Izmir");
        port.setCountry("Turkey");
        port.setPortName("Adnan Menderes Airport");
        port.setCode("ADB");
        ports.add(port);

        port = new Port();
        port.setCity("Antalya");
        port.setCountry("Turkey");
        port.setPortName("Antalya Airport");
        port.setCode("AYT");
        ports.add(port);

        port = new Port();
        port.setCity("Bodrum");
        port.setCountry("Turkey");
        port.setPortName("Milas-Bodrum Airport");
        port.setCode("BJV");
        ports.add(port);

        portRepository.saveAll(ports);
        
    }
    
}
