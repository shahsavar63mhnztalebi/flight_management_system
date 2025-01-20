package ir.barook.flightSearchService.service;

import ir.barook.flightSearchService.dto.FlightSearchRequestDto;
import ir.barook.flightSearchService.dto.FlightSearchResponseDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FlightSearchService {
    CompletableFuture<List<FlightSearchResponseDto>> searchFlightsByDate(FlightSearchRequestDto requestDto);
}
