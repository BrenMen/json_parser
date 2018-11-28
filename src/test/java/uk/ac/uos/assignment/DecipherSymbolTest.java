package uk.ac.uos.assignment;

import java.io.IOException;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DecipherSymbolTest {

	DecipherSymbol Decipher;
	
	@Before
	public void setup() {
		Decipher = new DecipherSymbol(new StringReader(""));
	}

	@Test
	public void StopBeforeEnd() throws IOException, CustomException {
		Decipher = new DecipherSymbol(new StringReader("."));
		Symbol symbol = Decipher.next();
		symbol = Decipher.next();
		assertEquals(null, symbol);
	}
	
	@Test
	public void Invalid() throws IOException, CustomException {
		Decipher = new DecipherSymbol(new StringReader("ThisShouldReturnNull"));
		Symbol symbol = Decipher.next();
		assertEquals(null, symbol);
	}
	
	@Test
	public void LeftCurlyBracket() throws IOException, CustomException {
		Decipher = new DecipherSymbol(new StringReader("{"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.LEFT_CURLY_BRACKET, symbol.type);
	}
	
	@Test
	public void RightCurlyBracket() throws IOException, CustomException {
		Decipher = new DecipherSymbol(new StringReader("}"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.RIGHT_CURLY_BRACKET, symbol.type);
	}
	
	@Test
	public void LeftSquareBracket() throws IOException, CustomException {
		Decipher = new DecipherSymbol(new StringReader("["));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.LEFT_SQUARE_BRACKET, symbol.type);
	}

	@Test
	public void RightSquareBracket() throws IOException, CustomException {
		Decipher = new DecipherSymbol(new StringReader("]"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.RIGHT_SQUARE_BRACKET, symbol.type);
	}
	
	@Test
	public void Colon() throws IOException, CustomException {
		Decipher = new DecipherSymbol(new StringReader(":"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.COLON, symbol.type);
	}
	
	@Test
	public void Comma() throws IOException, CustomException {
		Decipher = new DecipherSymbol(new StringReader(","));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.COMMA, symbol.type);
	}
	
	@Test
	public void Space() throws IOException, CustomException {
		Decipher = new DecipherSymbol(new StringReader(" "));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.SPACE, symbol.type);
	}
	
	@Test
	public void MultipleSpace() throws IOException, CustomException {
		Decipher = new DecipherSymbol(new StringReader("   "));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.SPACE, symbol.type);
	}
	
	@Test
	public void NewLine () throws IOException, CustomException {
		Decipher = new DecipherSymbol(new StringReader("\n"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.SPACE, symbol.type);
	}
	
	@Test
	public void SymbolAfterSpace() throws IOException, CustomException {
		Decipher = new DecipherSymbol(new StringReader(" :"));
		Symbol symbol = Decipher.next();
		assertEquals(Symbol.Type.SPACE, symbol.type);
		symbol = Decipher.next();
		assertEquals(Symbol.Type.COLON, symbol.type);
	}
	
//	@Test
//	public void testString() throws IOException, CustomException {
//		Decipher = new DecipherSymbol(new StringReader("\"This is a string\""));
//		Symbol symbol = Decipher.next();
//		assertEquals(Symbol.Type.STRING, symbol.type);
//		assertEquals("This is a string", symbol.value);
//	}
	
	
// String
// Integer
// True Boolean
// False Boolean
// Null Boolean
	
	
	
}