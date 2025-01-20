package ir.barook.flightSearchService.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FlightSearchResponseDto {
    private Long id;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private BigDecimal price;
    private Integer flightNumber;
}
