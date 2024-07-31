package com.hitit.project.microservices.flight_app.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Entity
@Data
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long f_id;
    private LocalDate date;
    private LocalTime time;
    @ManyToOne
    @JoinColumn(name = "departure_port_id")
    private Port departurePort;
    @ManyToOne
    @JoinColumn(name = "arrival_port_id")
    private Port arrivalPort;

    public Flight() {
    }

    public Long getF_id() {
        return f_id;
    }

    public void setF_id(Long f_id) {
        this.f_id = f_id;
    }

    

    public Port getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(Port departurePort) {
        this.departurePort = departurePort;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public void setArrivalPort(Port arrivalPort) {
        this.arrivalPort = arrivalPort;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Flight [f_id=" + f_id + ", date=" + date + ", time=" + time + ", departurePort=" + departurePort
                + ", arrivalPort=" + arrivalPort + "]";
    }

    
}