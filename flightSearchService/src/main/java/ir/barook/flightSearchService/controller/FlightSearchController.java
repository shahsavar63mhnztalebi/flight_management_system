package ir.barook.flightSearchService.controller;

import ir.barook.flightSearchService.dto.ApiDto;
import ir.barook.flightSearchService.dto.FlightSearchRequestDto;
import ir.barook.flightSearchService.dto.FlightSearchResponseDto;
import ir.barook.flightSearchService.service.FlightSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class FlightSearchController {
    @Autowired
    private FlightSearchService flightSearchService;


    @GetMapping
    public ApiDto<FlightSearchResponseDto> searchFlights(@RequestBody FlightSearchRequestDto requestDto) {
        try {
            List<FlightSearchResponseDto> flights = flightSearchService.searchFlightsByDate(requestDto);
            return new ApiDto(Boolean.FALSE, "successfully_get_flight_information", flights);
        } catch (Exception e) {
            // Handle the exception (e.g., log the error and return an error response)
            System.err.println("Error searching flights: " + e.getMessage());
            throw e;
        }
    }
}

