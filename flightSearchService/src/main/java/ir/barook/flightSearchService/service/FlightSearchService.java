package ir.barook.flightSearchService.service;

import ir.barook.flightSearchService.dto.FlightSearchRequestDto;
import ir.barook.flightSearchService.dto.FlightSearchResponseDto;

import java.util.List;

public interface FlightSearchService {
    List<FlightSearchResponseDto> searchFlightsByDate(FlightSearchRequestDto requestDto);
}
