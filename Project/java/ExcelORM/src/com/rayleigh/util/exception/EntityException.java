package com.rayleigh.util.exception;

public class EntityException extends Exception {

	private static final long serialVersionUID = -1956216016026597508L;

	public EntityException() {
	}

	public EntityException(String message) {
		super(message);
	}

	public EntityException(Throwable cause) {
		super(cause);
	}

	public EntityException(String message, Throwable cause) {
		super(message, cause);
	}

}
