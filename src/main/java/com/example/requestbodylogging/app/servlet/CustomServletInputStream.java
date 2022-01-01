package com.example.requestbodylogging.app.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

import lombok.SneakyThrows;

public class CustomServletInputStream extends ServletInputStream {
	private final InputStream cachedBodyInputStream;

	public CustomServletInputStream(byte[] cachedBody) {
		this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
	}

	@Override
	public int read() throws IOException {
		return cachedBodyInputStream.read();
	}

	@SneakyThrows
	@Override
	public boolean isFinished() {
		return cachedBodyInputStream.available() == 0;
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public void setReadListener(ReadListener readListener) {
		throw new UnsupportedOperationException();
	}
}
