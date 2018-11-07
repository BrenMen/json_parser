package uk.ac.uos.week6;


public class Number implements Describe {

	Double doubleNumber = 13.62546;
	@Override
	public String describe() {
		return Double.toString(doubleNumber);
	}
	
}