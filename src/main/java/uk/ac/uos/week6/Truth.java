package uk.ac.uos.week6;


public class Truth implements Describe {

	Boolean exampleBoolean = true;
	@Override
	public String describe() {
		return Boolean.toString(exampleBoolean);
	}
}