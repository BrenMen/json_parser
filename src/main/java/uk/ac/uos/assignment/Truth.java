package uk.ac.uos.assignment;


public class Truth implements Describe {

	Boolean exampleBoolean = true;
	@Override
	public String describe() {
		return Boolean.toString(exampleBoolean);
	}
}