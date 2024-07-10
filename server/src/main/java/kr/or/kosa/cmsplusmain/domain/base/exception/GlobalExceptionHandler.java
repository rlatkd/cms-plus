package kr.or.kosa.cmsplusmain.domain.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	// @ExceptionHandler(EntityNotFoundException.class)
	// public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
	// 	return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	// }
	//
	// @ExceptionHandler(MethodArgumentNotValidException.class)
	// public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
	// 	return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	// }
	//
	// @ExceptionHandler(IllegalArgumentException.class)
	// public ResponseEntity<String> handleMethodArgumentNotValidException(IllegalArgumentException ex) {
	// 	return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	// }
}
