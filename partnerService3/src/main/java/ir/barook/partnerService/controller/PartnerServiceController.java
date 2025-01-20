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
@RequestMapping("/partner3/api/flights/")
public class PartnerServiceController {

    @PostMapping("/search")
    public ResponseEntity<List<FlightSearchResponseDto>> searchFlights(@RequestBody SearchFlightRequestDto request) {
        List<FlightSearchResponseDto> partner1Flights = Arrays.asList(
                new FlightSearchResponseDto("THR", "AHV", LocalDate.of(2025, 1, 2), LocalTime.of(14, 30), BigDecimal.valueOf(630), 230),
                new FlightSearchResponseDto("THR", "AHV", LocalDate.of(2025, 1, 2), LocalTime.of(15, 30), BigDecimal.valueOf(130), 330),
                new FlightSearchResponseDto("THR", "AHV", LocalDate.of(2025, 1, 2), LocalTime.of(16, 30), BigDecimal.valueOf(230), 430),
                new FlightSearchResponseDto( "THR", "AHV", LocalDate.of(2025, 1, 2), LocalTime.of(17, 30), BigDecimal.valueOf(830), 930));

        return ResponseEntity.ok(partner1Flights);
    }
}
