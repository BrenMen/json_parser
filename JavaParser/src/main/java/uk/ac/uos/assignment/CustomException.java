package src.main.java.uk.ac.uos.assignment;

// Class built to contain a constructor that allows me to pass
// custom messages into the exception.

// I do not need to add CustomException to the serial stream
// therefore I am suppressing this warning.
@SuppressWarnings("serial")
public class CustomException extends Exception {

	public CustomException(String customMessage) {
		super(customMessage);
	}
}