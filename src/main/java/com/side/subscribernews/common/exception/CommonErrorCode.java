package com.side.subscribernews.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements BaseErrorCode {

	USER_NOT_EXISTS("USER_NOT_EXISTS","존재하지 않은 사용자입니다.", HttpStatus.NOT_FOUND),

	// 회원가입 시 이미 존재하는 사용자
	USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "이미 가입된 사용자입니다.", HttpStatus.CONFLICT);

	private final String errorCode;
	private final String message;
	private final HttpStatus status;
}
