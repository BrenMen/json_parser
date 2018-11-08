package uk.ac.uos.assignment;

public class Invalid implements Describe {
	private String invalid;
	public Invalid(String invalid) {
		this.invalid = invalid;
	}
	@Override
	public String describe() {
		return invalid;
	}
}