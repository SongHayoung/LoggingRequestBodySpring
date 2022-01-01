package com.example.requestbodylogging.app.advice;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.requestbodylogging.app.helper.RequestBodyAOPContextHelper;
import com.example.requestbodylogging.app.helper.RequestBodyAdviceContextHelper;
import com.example.requestbodylogging.app.servlet.CustomHttpServletRequestWrapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionAdvice {
	private static final String NPE = "NullPointerException";
	private static final String IAE = "IllegalArgumentException";
	private static final String CCE = "ClassCastException";

	private final RequestBodyAdviceContextHelper requestBodyAdviceContextHelper;
	private final RequestBodyAOPContextHelper requestBodyAOPContextHelper;

	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<String> handleNullPointerException(NullPointerException ex, HttpServletRequest req) throws IOException {
		CustomHttpServletRequestWrapper customWrapper = new CustomHttpServletRequestWrapper(req);
		String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		log.error("Request Body from custom wrapper [{}]", body);

		return ResponseEntity.internalServerError().body(NPE);
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest req) {
		log.error("Request Body from advice context [{}]", requestBodyAdviceContextHelper.getRequestBodyJsonString());

		return ResponseEntity.internalServerError().body(IAE);
	}

	@ExceptionHandler(value = ClassCastException.class)
	public ResponseEntity<String> handleClassCastException(ClassCastException ex, HttpServletRequest req) {
		log.error("Request Body from aop context [{}]", requestBodyAOPContextHelper.getRequestBodyJsonString());

		return ResponseEntity.internalServerError().body(CCE);
	}
}
