package ir.barook.flightManagementService.repository;

import ir.barook.flightManagementService.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByOriginAndDestinationAndDepartureDate(
            String origin, String destination, LocalDate departureDate);
}

