package com.side.subscribernews.common.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

	String getErrorCode();
	String getMessage();
	HttpStatus getStatus();
}
