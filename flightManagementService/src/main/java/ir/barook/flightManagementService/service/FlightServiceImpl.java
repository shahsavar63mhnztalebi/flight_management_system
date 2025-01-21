package ir.barook.flightManagementService.service;

import ir.barook.flightManagementService.dto.FlightSearchRequestDto;
import ir.barook.flightManagementService.dto.FlightSearchResponseDto;
import ir.barook.flightManagementService.exceptions.FlightNotFoundException;
import ir.barook.flightManagementService.mapper.ResponseDtoMapper;
import ir.barook.flightManagementService.model.Flight;
import ir.barook.flightManagementService.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService{

    private final FlightRepository flightRepository;
    private final ResponseDtoMapper responseDtoMapper;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, ResponseDtoMapper responseDtoMapper) {
        this.flightRepository = flightRepository;
        this.responseDtoMapper = responseDtoMapper;
    }

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

    public List<FlightSearchResponseDto> searchFlights(FlightSearchRequestDto requestDto) {
        List<Flight> flights = flightRepository.findByOriginAndDestinationAndDepartureDate(
                               requestDto.getOrigin(), requestDto.getDestination(), requestDto.getDepartureDate());

        return flights.stream().map(responseDtoMapper::mapToFlightSearchResponseDto).collect(Collectors.toList());
    }
}
