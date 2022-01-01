package com.example.requestbodylogging.app.helper;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;
import lombok.Setter;

@RequestScope
@Component
@Getter
@Setter
public class RequestBodyAOPContextHelper {
	private String requestBodyJsonString;
	private HttpServletRequest request;
}
