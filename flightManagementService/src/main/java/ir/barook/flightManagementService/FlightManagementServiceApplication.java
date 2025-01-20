package ir.barook.flightManagementService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FlightManagementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightManagementServiceApplication.class, args);
    }
}