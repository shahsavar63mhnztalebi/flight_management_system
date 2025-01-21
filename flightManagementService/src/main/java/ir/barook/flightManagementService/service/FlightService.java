package ir.barook.flightManagementService.service;

import ir.barook.flightManagementService.dto.FlightSearchRequestDto;
import ir.barook.flightManagementService.dto.FlightSearchResponseDto;
import ir.barook.flightManagementService.model.Flight;

import java.util.List;

public interface FlightService {
    Flight getFlightById(Long id);
    Flight addFlight(Flight flight);
    void deleteFlight(Long id);
    Flight updateFlight(Long id, Flight flightDetails);
    List<Flight> getAllFlights();

    List<FlightSearchResponseDto> searchFlights(FlightSearchRequestDto requestDto);
}
