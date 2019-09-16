package com.sample.encryptApplication.exception;

public class EncryptException extends RuntimeException {

	public EncryptException(){
		super("Could not Encrypt values");
	}

}
