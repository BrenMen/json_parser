package uk.ac.uos.week6;


public class Text implements Describe {

	String exampleString = "This is a string";
	@Override
	public String describe() {
		return "\"" + exampleString + "\"";
	}
	
}