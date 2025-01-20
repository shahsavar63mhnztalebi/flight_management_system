package ir.barook.flightSearchService.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class SearchFlightRequestDto {
    private String origin;
    private String destination;
    private LocalDate departureDate;
}
