package uk.ac.uos.assignment;


public class Invalid implements Describe {

	String exampleString = null;
	@Override
	public String describe() {
		return "\"" + exampleString + "\"";
	}
	
}