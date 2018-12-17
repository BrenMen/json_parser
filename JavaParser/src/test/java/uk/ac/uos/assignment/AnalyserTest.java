package src.test.java.uk.ac.uos.assignment;

import java.io.IOException;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

import uk.ac.uos.parser.CustomException;
import uk.ac.uos.parser.JSONAnalyser;
import uk.ac.uos.parser.JSONSymbol;

import static org.junit.Assert.*;

public class AnalyserTest {

	JSONAnalyser Decipher;

	@Before
	public void setup() {
		Decipher = new JSONAnalyser(new StringReader(""));
	}

	@Test
	public void stopBeforeEndTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("."));
		JSONSymbol symbol = Decipher.next();
		symbol = Decipher.next();
		assertEquals(null, symbol);
	}

	@Test
	public void invalidTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("ThisShouldReturnNull"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(null, symbol);
	}

	@Test
	public void leftCurlyBracketTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("{"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.LEFT_CURLY_BRACKET, symbol.type);
	}

	@Test
	public void rightCurlyBracketTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("}"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.RIGHT_CURLY_BRACKET, symbol.type);
	}

	@Test
	public void leftSquareBracketTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("["));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.LEFT_SQUARE_BRACKET, symbol.type);
	}

	@Test
	public void rightSquareBracketTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("]"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.RIGHT_SQUARE_BRACKET, symbol.type);
	}
	
	@Test
	public void twoBracketsTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("[{"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.LEFT_SQUARE_BRACKET, symbol.type);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.LEFT_CURLY_BRACKET, symbol.type);
	}

	@Test
	public void colonTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader(":"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.COLON, symbol.type);
	}

	@Test
	public void commaTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader(","));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.COMMA, symbol.type);
	}

	@Test
	public void spaceTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader(" "));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.SPACE, symbol.type);
	}

	@Test
	public void tabTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("	"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.SPACE, symbol.type);
	}

	@Test
	public void newLineTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("\n"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.SPACE, symbol.type);
	}

	@Test
	public void colonAfterSpaceTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader(" :"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.SPACE, symbol.type);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.COLON, symbol.type);
	}

	@Test
	public void stringTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("\"This is a string\""));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.STRING, symbol.type);
		assertEquals("This is a string", symbol.value);
	}

	@Test
	public void stringWithSymbolTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("\"This is a string\", []"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.STRING, symbol.type);
		assertEquals("This is a string", symbol.value);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.COMMA, symbol.type);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.SPACE, symbol.type);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.LEFT_SQUARE_BRACKET, symbol.type);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.RIGHT_SQUARE_BRACKET, symbol.type);
	}

	@Test
	public void nullTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("null"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.NULL_BOOLEAN, symbol.type);
	}
	
	@Test
	public void invalidNulvTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("nulv"));
		boolean exception = false;
		String customMessage = "";
		try {
			Decipher.next();
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid Null value.", customMessage);
	}
	
	@Test
	public void invalidNuvvTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("nuvv"));
		boolean exception = false;
		String customMessage = "";
		try {
			Decipher.next();
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid Null value.", customMessage);
	}
	
	@Test
	public void invalidNvvvTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("nvvv"));
		boolean exception = false;
		String customMessage = "";
		try {
			Decipher.next();
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid Null value.", customMessage);
	}

	@Test
	public void trueTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("true"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.TRUE_BOOLEAN, symbol.type);
	}

	@Test
	public void invalidTruvTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("truv"));
		boolean exception = false;
		String customMessage = "";
		try {
			Decipher.next();  
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid True value.", customMessage);
	}
	
	@Test
	public void invalidTrvvTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("trvv"));
		boolean exception = false;
		String customMessage = "";
		try {
			Decipher.next();  
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid True value.", customMessage);
	}
	
	@Test
	public void invalidTvvvTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("tvvv"));
		boolean exception = false;
		String customMessage = "";
		try {
			Decipher.next();  
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid True value.", customMessage);
	}

	@Test
	public void falseTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("false"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.FALSE_BOOLEAN, symbol.type);
	}
	
	@Test
	public void invalidFalsvTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("falsv"));
		boolean exception = false;
		String customMessage = "";
		try {
			Decipher.next();
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid False value.", customMessage);
	}
	
	@Test
	public void invalidFalvvTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("falvv"));
		boolean exception = false;
		String customMessage = "";
		try {
			Decipher.next();
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid False value.", customMessage);
	}
	
	@Test
	public void invalidFavvvTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("favvv"));
		boolean exception = false;
		String customMessage = "";
		try {
			Decipher.next();
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid False value.", customMessage);
	}

	@Test
	public void invalidFvvvvTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("fvvvv"));
		boolean exception = false;
		String customMessage = "";
		try {
			Decipher.next();
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid False value.", customMessage);
	}
	
	@Test
	public void numberDigitTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("5"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.NUMBER, symbol.type);
	}
	
	@Test
	public void numberMinusTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("-5"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.NUMBER, symbol.type);
	}
	
	@Test
	public void numberPlusTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("4+4"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.NUMBER, symbol.type);
	}

	@Test
	public void numberLowercaseExponentialTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("53e"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.NUMBER, symbol.type);
	}
	
	@Test
	public void numberUppercaseExponentialTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("53E+2"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.NUMBER, symbol.type);
	}
	
	@Test
	public void numberDecimalTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("14.37"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.NUMBER, symbol.type);
	}
	
	@Test
	public void numberStringBooleanInArrayTest() throws IOException, CustomException {
		Decipher = new JSONAnalyser(new StringReader("[532e-6, \"String\", true]"));
		JSONSymbol symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.LEFT_SQUARE_BRACKET, symbol.type);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.NUMBER, symbol.type);
		assertEquals("532e-6", symbol.value);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.COMMA, symbol.type);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.SPACE, symbol.type);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.STRING, symbol.type);
		assertEquals("String", symbol.value);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.COMMA, symbol.type);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.SPACE, symbol.type);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.TRUE_BOOLEAN, symbol.type);
		symbol = Decipher.next();
		assertEquals(JSONSymbol.Type.RIGHT_SQUARE_BRACKET, symbol.type);
	}
}