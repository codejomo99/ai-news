package com.side.subscribernews.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<?> handleBaseException(BaseException ex) {
		BaseErrorCode errorCode = ex.getErrorCode();
		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ErrorResponse.of(errorCode.getErrorCode(), errorCode.getMessage()));
	}


	@Getter
	@AllArgsConstructor
	private static class ErrorResponse {
		private final ErrorDetail error;

		static ErrorResponse of(String errorCode, String message) {
			return new ErrorResponse(new ErrorDetail(errorCode,message));
		}
	}

	@Getter
	@AllArgsConstructor
	private static class ErrorDetail {
		private final String code;
		private final String message;
	}
}