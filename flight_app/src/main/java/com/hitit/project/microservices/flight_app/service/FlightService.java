package com.hitit.project.microservices.flight_app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitit.project.microservices.flight_app.entity.Flight;
import com.hitit.project.microservices.flight_app.entity.FlightDetails;
import com.hitit.project.microservices.flight_app.entity.Port;
import com.hitit.project.microservices.flight_app.repository.FlightRepository;


@Service
public class FlightService {
    
    @Autowired
    private FlightRepository flightRepository;

    @Autowired 
    FlightDetailsService flightDetailsService;

    @Autowired
    private PortService portService;

    
    public List<Flight> findFlightsByDateAndPorts(Long departurePortId, Long arrivalPortId, LocalDate date) {

        return flightRepository.findFlightsByDateAndPorts(departurePortId, arrivalPortId, date);
    }

    public Flight findById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }

    public List<Flight> searchFlights(String departurePort, String arrivalPort, LocalDate departureDate, int adults,
        int children, int infants) {

        int total_pass = adults + children + infants;   

        List<Flight> outpuList = new ArrayList<Flight>();

        long departurePortId = portService.findByCode(departurePort).getPort_id();
        long arrivalPortId = portService.findByCode(arrivalPort).getPort_id();
        
        List<Flight> flights = flightRepository.findFlightsByDateAndPorts(departurePortId, arrivalPortId, departureDate);

        

        for (Flight flight : flights) {
            if (flightDetailsService.getAvailableSeatsByFlightId(flight.getF_id()) >= total_pass) {
                outpuList.add(flight);
            }
        }

        outpuList.sort((flight1, flight2) -> flight1.getTime().compareTo(flight2.getTime()));
        
        return outpuList;
    }


    public void generateFlightsAndDetails() {
        List<Port> ports = portService.findAll();

        for (int i = 0; i < ports.size(); i++) {
            for (int j = 0; j < ports.size(); j++) {
                if (i != j) {
                    Port departurePort = ports.get(i);
                    Port arrivalPort = ports.get(j);

                    int numFlights = 10;

                    for (int k = 0; k < numFlights; k++) {
                        Flight flight = new Flight();
                        flight.setDate(LocalDate.now().plusDays((int) (Math.random() * 3)));
                        flight.setDeparturePort(departurePort);
                        flight.setArrivalPort(arrivalPort);
                        flight.setTime(LocalTime.of((int) (Math.random() * 24), (int) (Math.random() * 60)));

                        flightRepository.save(flight);
                        createFlightDetails(flight);
                    }
                }
            }
        }
    }

    private void createFlightDetails(Flight flight) {
        String[] cabins = {"Economy", "Business", "First Class"};
        Double[] prices = {100.0, 300.0, 1000.0};
        Integer[] seats = {150, 50, 10};

        for (int i = 0; i < cabins.length; i++) {
            FlightDetails details = new FlightDetails();
            details.setCabin(cabins[i]);
            details.setPrice(prices[i]);
            details.setAvailableSeats(seats[i]);
            details.setFlight(flight);

            flightDetailsService.save(details);
        }
    }


    
}
