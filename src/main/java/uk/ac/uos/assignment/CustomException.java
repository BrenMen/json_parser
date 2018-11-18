package uk.ac.uos.assignment;

//Class built to contain a constructor that allows me to pass
//custom messages into the exception.
public class CustomException extends Exception {
	
	public CustomException(String customMessage) {
		super(customMessage);
	}
}