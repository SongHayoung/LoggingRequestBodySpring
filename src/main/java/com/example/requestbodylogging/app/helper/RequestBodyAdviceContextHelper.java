package com.example.requestbodylogging.app.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;
import lombok.Setter;

@RequestScope
@Component
@Getter
@Setter
public class RequestBodyAdviceContextHelper {
	private String requestBodyJsonString;
}
