package uk.ac.uos.week6;


public class Invalid implements Describe {

	String exampleString = null;
	@Override
	public String describe() {
		return "\"" + exampleString + "\"";
	}
	
}