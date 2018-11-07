package uk.ac.uos.assignment;


public class Text implements Describe {

	String exampleString = "This is a string";
	@Override
	public String describe() {
		return "\"" + exampleString + "\"";
	}
	
}