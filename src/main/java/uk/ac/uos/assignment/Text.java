package uk.ac.uos.assignment;

public class Text implements Describe {
	private String text;
	public Text(String text) {
		this.text = text;
	}
	@Override
	public String describe() {
		return "\"" + text + "\"";
	}
}