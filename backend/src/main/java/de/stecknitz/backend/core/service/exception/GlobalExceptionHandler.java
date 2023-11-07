package de.stecknitz.backend.core.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StockNotFoundException.class)
    ProblemDetail handleStockNotFoundException(StockNotFoundException stockNotFoundException) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, stockNotFoundException.getMessage());
        problemDetail.setTitle("Stock not found");
        return problemDetail;
    }

    @ExceptionHandler(UserNotFoundException.class)
    ProblemDetail handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, userNotFoundException.getMessage());
        problemDetail.setTitle("User not found");
        return problemDetail;
    }

    @ExceptionHandler(DepotNotFoundException.class)
    ProblemDetail handleDepotNotFoundException(DepotNotFoundException depotNotFoundException) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, depotNotFoundException.getMessage());
        problemDetail.setTitle("Depot not found");
        return problemDetail;
    }

    @ExceptionHandler(StockAlreadyExistsException.class)
    ProblemDetail handleStockAlreadyExistsException(StockAlreadyExistsException stockAlreadyExistsException) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, stockAlreadyExistsException.getMessage());
        problemDetail.setTitle("Stock already exists");
        return problemDetail;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    ProblemDetail handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, userAlreadyExistsException.getMessage());
        problemDetail.setTitle("User already exists");
        return problemDetail;
    }

}
