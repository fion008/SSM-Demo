package com.test.base.exception;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 2908177368884248025L;

	public BaseException() {
	}

	public BaseException(String m, Throwable c) {
		super(m, c);
	}

	public BaseException(String m) {
		super(m);
	}

	public BaseException(Throwable c) {
		super(c);
	}

	private Integer code;

	public BaseException(Integer code, String m) {
		super(m);
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String toJsonString() throws Exception {
		HashMap<String, String> r = new HashMap<String, String>();
		if (code != null) {
			r.put("code", String.valueOf(code));
		} else {
			r.put("code", "5001");
		}
		r.put("message", this.getMessage());
		return new ObjectMapper().writeValueAsString(r);
	}
}
