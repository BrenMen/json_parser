package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import uk.ac.uos.test.SimpleMath;

class SimpleMathTest {

	@Test
	public void test() {
		SimpleMath test = new SimpleMath();
		int output = test.square(5);
		assertEquals(25, output);
	}
}