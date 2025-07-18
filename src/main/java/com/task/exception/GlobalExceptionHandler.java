package com.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, "Invalid parameter: " + ex.getName());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGeneralException(Exception ex) {
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred");
	}

	private ResponseEntity<?> buildResponse(HttpStatus status, String message) {
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", status.value());
		error.put("error", status.getReasonPhrase());
		error.put("message", message);
		return new ResponseEntity<>(error, status);
	}
}
