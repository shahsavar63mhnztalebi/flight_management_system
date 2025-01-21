package ir.barook.flightManagementService.mapper;
import ir.barook.flightManagementService.dto.FlightSearchResponseDto;
import ir.barook.flightManagementService.model.Flight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseDtoMapper {
        FlightSearchResponseDto mapToFlightSearchResponseDto(Flight flight);
}
