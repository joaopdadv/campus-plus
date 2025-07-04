package br.com.campus.campus.config;

import br.com.campus.campus.entity.auth.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildResponse(Exception ex, HttpServletRequest request, HttpStatus status) {
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex, HttpServletRequest request) {
        return this.buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception ex, HttpServletRequest request) {
        return this.buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

}
