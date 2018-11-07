package assignmentTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.uos.assignment.Number;
import uk.ac.uos.assignment.Text;
import uk.ac.uos.assignment.Truth;


class Week6Test {
	
	@Test
	void truthTest() {
		Truth truthTest = new Truth();
		String output = truthTest.describe();
		assertEquals("true", output);
	}

	@Test
	void numberTest() {
		Number numberTest = new Number();
		String output = numberTest.describe();
		assertEquals("13.62546", output);
	}
	
	@Test
	void textTest() {
		Text textTest = new Text();
		String output = textTest.describe();
		assertEquals("\"This is a string\"", output);
	}
}



	
	
	