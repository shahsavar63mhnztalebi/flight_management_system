package ir.barook.flightManagementService.model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@ToString
@Access(value = AccessType.FIELD)
@Table(name = "T_FLIGHT")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Origin cannot be empty")
    private String origin;

    @NotEmpty(message = "destination cannot be empty")
    private String destination;

    @NotNull(message = "departureDate cannot be empty")
    private LocalDate departureDate;

    @NotNull(message = "departureTime cannot be empty")
    private LocalTime departureTime;

    @NotNull(message = "price cannot be empty")
    @Min(value = 0, message = "Price must be non-negative")
    private BigDecimal price;

    @Column(unique = true)
    @NotNull(message = "flightNumber cannot be null")
    private Integer flightNumber;
}

