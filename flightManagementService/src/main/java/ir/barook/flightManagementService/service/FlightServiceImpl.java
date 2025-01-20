package ir.barook.flightManagementService.service;

import ir.barook.flightManagementService.dto.SearchFlightRequestDto;
import ir.barook.flightManagementService.exceptions.FlightNotFoundException;
import ir.barook.flightManagementService.model.Flight;
import ir.barook.flightManagementService.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService{

    @Autowired
    private FlightRepository flightRepository;

     public Flight getFlightById(Long id) {
         return flightRepository.findById(id) .orElseThrow(() -> new FlightNotFoundException(id));
     }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
     public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    public Flight updateFlight(Long id, Flight flightDetails) {
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new FlightNotFoundException(id));
        flight.setOrigin(flightDetails.getOrigin());
        flight.setDestination(flightDetails.getDestination());
        flight.setDepartureTime(flightDetails.getDepartureTime());
        flight.setFlightNumber(flightDetails.getFlightNumber());
        flight.setPrice(flightDetails.getPrice());
        return flightRepository.save(flight);
    }

    public List<Flight> searchFlights(SearchFlightRequestDto requestDto) {
        return flightRepository.findByOriginAndDestinationAndDepartureDate(
                requestDto.getOrigin(), requestDto.getDestination(), requestDto.getDepartureDate());
    }
}
