package com.restaurant.exception;

import com.restaurant.model.dto.response.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.StringJoiner;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidArguments(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        StringJoiner validationMessages = new StringJoiner(", ");
        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                validationMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage())
        );
        String message = validationMessages.length() == 0
                ? "Solicitud inválida"
                : validationMessages.toString();

        log.warn("Request validation failed for {}: {}", request.getRequestURI(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.of(HttpStatus.BAD_REQUEST, message, request.getRequestURI()));
    }

    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleDishNotFound(
            DishNotFoundException exception,
            HttpServletRequest request
    ) {
        log.warn("Dish not found for {}: {}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.of(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(WokToppingsLimitExceededException.class)
    public ResponseEntity<ErrorResponseDTO> handleWokToppingsLimitExceeded(
            WokToppingsLimitExceededException exception,
            HttpServletRequest request
    ) {
        log.warn("Wok toppings limit exceeded for {}: {}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorResponseDTO.of(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(TableNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleTableNotFound(
            TableNotFoundException exception,
            HttpServletRequest request
    ) {
        log.warn("Table not found for {}: {}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.of(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(BillNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleBillNotFound(
            BillNotFoundException exception,
            HttpServletRequest request
    ) {
        log.warn("Bill not found for {}: {}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.of(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(ActiveBillExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleActiveBillExists(
            ActiveBillExistsException exception,
            HttpServletRequest request
    ) {
        log.warn("Cannot open bill for {}: {}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponseDTO.of(HttpStatus.CONFLICT, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(ReservationConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleReservationConflict(
            ReservationConflictException exception,
            HttpServletRequest request
    ) {
        log.warn("Reservation conflict for {}: {}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponseDTO.of(HttpStatus.CONFLICT, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleReservationNotFound(
            ReservationNotFoundException exception,
            HttpServletRequest request
    ) {
        log.warn("Reservation not found for {}: {}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.of(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(ParkingFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleParkingFull(
            ParkingFullException exception,
            HttpServletRequest request
    ) {
        log.warn("Parking capacity exceeded for {}: {}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorResponseDTO.of(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(VehicleAlreadyActiveException.class)
    public ResponseEntity<ErrorResponseDTO> handleVehicleAlreadyActive(
            VehicleAlreadyActiveException exception,
            HttpServletRequest request
    ) {
        log.warn("Vehicle already active for {}: {}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponseDTO.of(HttpStatus.CONFLICT, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleVehicleNotFound(
            VehicleNotFoundException exception,
            HttpServletRequest request
    ) {
        log.warn("Active vehicle not found for {}: {}", request.getRequestURI(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.of(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler({NoResourceFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleRouteNotFound(
            Exception exception,
            HttpServletRequest request
    ) {
        log.warn("Route not found: {}", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.of(HttpStatus.NOT_FOUND, "Ruta no encontrada", request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleUnexpectedException(
            Exception exception,
            HttpServletRequest request
    ) {
        log.error("Unexpected error while processing {}", request.getRequestURI(), exception);
        String message = "Algo salió mal, intenta de nuevo";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseDTO.of(HttpStatus.INTERNAL_SERVER_ERROR, message, request.getRequestURI()));
    }
}