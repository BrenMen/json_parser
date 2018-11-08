package uk.ac.uos.assignment;

public class Truth implements Describe {
	private Boolean truth;
	public Truth(Boolean truth) {
		this.truth = truth;
	}
	@Override
	public String describe() {
		return Boolean.toString(truth);
	}
}
