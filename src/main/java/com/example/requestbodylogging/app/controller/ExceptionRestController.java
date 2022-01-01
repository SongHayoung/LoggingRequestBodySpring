package com.example.requestbodylogging.app.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.requestbodylogging.app.annotation.RequestBodyHijacking;
import com.example.requestbodylogging.app.model.SimpleBody;

@RestController
public class ExceptionRestController {
	@PostMapping(value = "/exception/npe", produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean throwNullPointerException(@RequestBody SimpleBody body) {
		throw new NullPointerException();
	}

	@PostMapping(value = "/exception/iae", produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean throwIllegalArgumentException(@RequestBody SimpleBody body) {
		throw new IllegalArgumentException();
	}


	@PostMapping(value = "/exception/cce", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestBodyHijacking
	public Boolean throwClassCastException(@RequestBody SimpleBody body) {
		throw new ClassCastException();
	}
}
