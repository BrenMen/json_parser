package uk.ac.uos.assignment;

import java.io.IOException;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnalyserTest {

	Analyser Decipher;
	
	@Before
	public void setup() {
		Decipher = new Analyser(new StringReader(""));
	}

	@Test
	public void StopBeforeEnd() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader("."));
		Symbol symbol = Decipher.next();
		symbol = Decipher.next();
		assertEquals(null, symbol);
	}
	
	@Test
	public void Invalid() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader("ThisShouldReturnNull"));
		Symbol symbol = Decipher.next();
		assertEquals(null, symbol);
	}
	
	@Test
	public void LeftCurlyBracket() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader("{"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.LEFT_CURLY_BRACKET, symbol.type);
	}
	
	@Test
	public void RightCurlyBracket() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader("}"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.RIGHT_CURLY_BRACKET, symbol.type);
	}
	
	@Test
	public void LeftSquareBracket() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader("["));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.LEFT_SQUARE_BRACKET, symbol.type);
	}

	@Test
	public void RightSquareBracket() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader("]"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.RIGHT_SQUARE_BRACKET, symbol.type);
	}
	
	//two brackets together
	
	@Test
	public void Colon() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader(":"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.COLON, symbol.type);
	}
	
	@Test
	public void Comma() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader(","));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.COMMA, symbol.type);
	}
	
	@Test
	public void Space() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader(" "));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.SPACE, symbol.type);
	}
	
	@Test
	public void Tab() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader("	"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.SPACE, symbol.type);
	}
	
	@Test
	public void NewLine () throws IOException, CustomException {
		Decipher = new Analyser(new StringReader("\n"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.SPACE, symbol.type);
	}
	
	@Test
	public void ColonAfterSpace() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader(" :"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.SPACE, symbol.type);
		symbol = Decipher.next();
		assertEquals(Symbol.Type.COLON, symbol.type);
	}
	
	@Test
	public void String() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader("\"This is a string\""));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.STRING, symbol.type);
		assertEquals("This is a string", symbol.value);
	}
	
	//string with symbol
	
	@Test
	public void Null() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader("null"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.NULL_BOOLEAN, symbol.type);
	}
	
	//test invalid null
	
	@Test
	public void True() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader("true"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.TRUE_BOOLEAN, symbol.type);
	}
	
	//test invalid true
	
	@Test
	public void False() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader("false"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.FALSE_BOOLEAN, symbol.type);
	}	
	
	//test invalid false
	
	@Test
	public void Number() throws IOException, CustomException {
		Decipher = new Analyser(new StringReader("5"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.NUMBER, symbol.type);
	}	
	
	//test number with e, -, +, ., etc.
	//test invalid number
	//test number with combination of string, colon etc in an array.
	//test large assortment of symbols.
	//test large and small exponentials
}