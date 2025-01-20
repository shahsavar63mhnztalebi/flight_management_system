package ir.barook.flightManagementService.controller;

import ir.barook.flightManagementService.dto.FlightSearchResponseDto;
import ir.barook.flightManagementService.dto.SearchFlightRequestDto;
import ir.barook.flightManagementService.model.Flight;
import ir.barook.flightManagementService.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlight() {
        List<Flight> flight = flightService.getAllFlights();
        return ResponseEntity.ok(flight);
    }

    @PostMapping("/add")
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
        Flight addedFlight = flightService.addFlight(flight);
        return ResponseEntity.ok(addedFlight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flightDetails) {
        Flight updatedFlight = flightService.updateFlight(id, flightDetails);
        return ResponseEntity.ok(updatedFlight);
    }

    @PostMapping("/search")
    public ResponseEntity<List<FlightSearchResponseDto>> searchFlights(@RequestBody SearchFlightRequestDto request) {
        List<Flight> flights = flightService.searchFlights(request);
        List<FlightSearchResponseDto> partner1Flights = Arrays.asList(
                new FlightSearchResponseDto(Long.valueOf(1), "THR", "AHV", LocalDate.of(2025, 1, 2), LocalTime.of(18, 30), BigDecimal.valueOf(600), 200),
                new FlightSearchResponseDto(Long.valueOf(1), "THR", "AHV", LocalDate.of(2025, 1, 2), LocalTime.of(17, 30), BigDecimal.valueOf(100), 300));

        return ResponseEntity.ok(partner1Flights);
    }
}

