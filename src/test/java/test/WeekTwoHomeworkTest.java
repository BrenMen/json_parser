package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import uk.ac.uos.week2.WeekTwoHomework;

class WeekTwoHomeworkTest {
	
	@Test
	// Tests that the full name is joined correctly with spacing
	public void JoinFormat() {
		WeekTwoHomework Format = new WeekTwoHomework();
		String output = Format.fullName("Alex", "Portman", "Jones");
		
		assertEquals("Alex P Jones", output);
	}

	@Test
	// Tests that each name uses camel case correctly
	public void CamelCase() {
		WeekTwoHomework CamelCase = new WeekTwoHomework();
		String output = CamelCase.fullName("jamie", "rick", "cole");
		
		assertEquals("Jamie R Cole", output);
	}
	
	@Test
	// Tests that middle name returns first letter capitalised
	public void MiddleFormat() {
		WeekTwoHomework MiddleFormat = new WeekTwoHomework();
		String output = MiddleFormat.fullName("Andrew", "Merlow", "Stud");
		
		assertEquals("Andrew M Stud", output);
	}
	
	@Test
	// Tests that middle name returns first letter capitalised
	public void MiddleValid() {
		WeekTwoHomework MiddleValid = new WeekTwoHomework();
		String output = MiddleValid.fullName("Andrew", "Merlow", "Stud");
		
		assertEquals("Andrew M Stud", output);
	}
}