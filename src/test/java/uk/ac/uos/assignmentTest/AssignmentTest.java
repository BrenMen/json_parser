package uk.ac.uos.assignmentTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import uk.ac.uos.week6.Truth;
import uk.ac.uos.week6.Invalid;
import uk.ac.uos.week6.Number;
import uk.ac.uos.week6.Text;
import uk.ac.uos.week6.Description;


class AssignmentTest {
	
	@Test
	void truthTest() {
		Truth truthTest = new Truth(true);
		String output = truthTest.describe();
		assertEquals("true", output);
	}

	@Test
	void numberTest() {
		Number numberTest = new Number(16.483);
		String output = numberTest.describe();
		assertEquals("16.483", output);
	}
	
	@Test
	void textTest() {
		Text textTest = new Text("ugh");
		String output = textTest.describe();
		assertEquals("\"ugh\"", output);
	}
	
	@Test void testDescription() {
		Description dd = new Description();
		dd.add(new Invalid());
//		collection[1] = new Number();
//		collection[2] = new Text("example");
//		collection[3] = new Truth();
		assertEquals("\"This is a string\"", dd.describe());
	}
}



	
	
	