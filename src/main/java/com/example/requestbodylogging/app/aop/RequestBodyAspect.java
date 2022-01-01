package com.example.requestbodylogging.app.aop;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.requestbodylogging.app.helper.RequestBodyAOPContextHelper;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RequestBodyAspect {
	private final Gson gson;
	private final RequestBodyAOPContextHelper contextHelper;

	@Before("@annotation(com.example.requestbodylogging.app.annotation.RequestBodyHijacking)")
	public void getRequestBody(JoinPoint pointCut) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		contextHelper.setRequest(request);

		MethodSignature signature = (MethodSignature) pointCut.getSignature();
		Method method = signature.getMethod();

		Arrays.stream(method.getParameters())
			.filter(p -> {
				try {
					return p.getAnnotation(RequestBody.class) != null;
				} catch (Exception e) {
					return false;
				}
			})
			.findFirst()
			.flatMap(p ->
				Arrays.stream(pointCut.getArgs())
					.filter(arg -> p.getType().equals(arg.getClass()))
					.findFirst()
			).ifPresent(arg -> contextHelper.setRequestBodyJsonString(gson.toJson(arg)));
	}
}
