package ir.barook.flightSearchService.config;

import ir.barook.flightSearchService.dto.ApiDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

@ControllerAdvice
@RestController
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, HttpServletRequest request) {
        ex.printStackTrace();
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        logger.severe("stack trace in handleAll: " + sw);
        ApiDto apiError = new ApiDto(Boolean.TRUE, "error_code(not_implemented)", null);
        logger.severe(ex.getClass().getSimpleName() + " thrown with message = " + ex.getMessage() + " for service " + request.getServletPath() + " and username = " + request.getParameter("userName"));
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
