package uk.ac.uos.assignment;

public class Number implements Describe {
	private Double number;
	public Number(Double number) {
		this.number = number;
	}
	@Override
	public String describe() {
		return Double.toString(number);
	}
}