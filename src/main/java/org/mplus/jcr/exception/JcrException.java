package org.mplus.jcr.exception;

public class JcrException extends RuntimeException{

	private static final long serialVersionUID = -8279616084211653198L;

	public JcrException(){
		super();
	}
	
	public JcrException(String message){
		super(message);
	}
	
	
	public JcrException(String message,Throwable cause){
		super(message, cause);
	}
}

