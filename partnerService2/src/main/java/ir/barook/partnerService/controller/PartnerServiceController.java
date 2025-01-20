package ir.barook.partnerService.controller;

import ir.barook.partnerService.dto.FlightSearchResponseDto;
import ir.barook.partnerService.dto.SearchFlightRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/partner2/api/flights/")
public class PartnerServiceController {

    @PostMapping("/search")
    public ResponseEntity<List<FlightSearchResponseDto>> searchFlights(@RequestBody SearchFlightRequestDto request) {
        List<FlightSearchResponseDto> partner1Flights = Arrays.asList(
                new FlightSearchResponseDto("THR", "AHV", LocalDate.of(2025, 1, 2), LocalTime.of(11, 00), BigDecimal.valueOf(610), 220),
                new FlightSearchResponseDto("THR", "AHV", LocalDate.of(2025, 1, 2), LocalTime.of(13, 00), BigDecimal.valueOf(110), 320),
                new FlightSearchResponseDto("THR", "AHV", LocalDate.of(2025, 1, 2), LocalTime.of(15, 00), BigDecimal.valueOf(210), 420),
                new FlightSearchResponseDto( "THR", "AHV", LocalDate.of(2025, 1, 2), LocalTime.of(10, 30), BigDecimal.valueOf(810), 910));

        return ResponseEntity.ok(partner1Flights);
    }
}
