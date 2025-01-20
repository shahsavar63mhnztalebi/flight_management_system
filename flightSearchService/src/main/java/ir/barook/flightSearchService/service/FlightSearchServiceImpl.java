package ir.barook.flightSearchService.service;

import ir.barook.flightSearchService.dto.FlightSearchRequestDto;
import ir.barook.flightSearchService.dto.FlightSearchResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class FlightSearchServiceImpl implements FlightSearchService {
    @Autowired
    private RestTemplate restTemplate;

    private static final List<String> PARTNER_API_URLS = Arrays.asList
            ("http://localhost:8080/api/flights/search",
                    "http://localhost:9001/partner1/api/flights/search",
                    "http://localhost:9002/partner2/api/flights/search",
                    "http://localhost:9003/partner3/api/flights/search"
            );

    @Cacheable(value = "search_flights_cache")
    public List<FlightSearchResponseDto> searchFlightsByDate(FlightSearchRequestDto requestDto) {
        List<CompletableFuture<List<FlightSearchResponseDto>>> futures = PARTNER_API_URLS.stream()
                .map(url -> CompletableFuture.supplyAsync(() -> searchPartnerFlights(url, requestDto)))
                .collect(Collectors.toList());
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        return allFutures.thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .collect(Collectors.toList()))
                .thenApply(this::combineAndSortFlights).join();
    }

    private List<FlightSearchResponseDto> searchPartnerFlights(String apiUrl, FlightSearchRequestDto requestDto) {
        Map<String, Object> params = new HashMap<>();
        params.put("origin", requestDto.getOrigin());
        params.put("destination", requestDto.getDestination());
        params.put("departureDate", requestDto.getDepartureDate());
        HttpHeaders headers = new HttpHeaders();
        headers.put("Content-Type", Collections.singletonList("application/json"));
        headers.put("Accept", Collections.singletonList("*/*"));
        try {

            ResponseEntity<List<FlightSearchResponseDto>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(params, headers),
                    new ParameterizedTypeReference<>() {
                    });

            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error fetching data from partner API: " + apiUrl);
            return Collections.emptyList();
        }
    }

    private List<FlightSearchResponseDto> combineAndSortFlights(List<FlightSearchResponseDto> responses) {
//        return responses.stream()
//                .sorted(Comparator.comparing(FlightSearchResponseDto::getPrice))
//                .collect(Collectors.toList());

        responses.sort(Comparator.comparing(FlightSearchResponseDto::getPrice));
        return responses;
    }
}
