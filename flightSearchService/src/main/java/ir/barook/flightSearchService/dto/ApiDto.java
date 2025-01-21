package ir.barook.flightSearchService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class ApiDto<T> {
    private Boolean hasError;
    private String code;
    private T data;
}
