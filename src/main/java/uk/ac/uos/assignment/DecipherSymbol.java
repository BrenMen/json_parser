package uk.ac.uos.assignment;

import java.io.StringReader;
import java.io.PushbackReader;
import java.io.IOException;


//Class reads through JSON and deciphers the symbol types
//of the JSON syntax
public class DecipherSymbol {
	
	//PushbackReader allowed me to move my position in the 
	//JSON back whenever I decided to read ahead more than one
	//character inside the JSON
	private PushbackReader pushbackReader;
	
	//Constructor that takes StringReader as its argument
	public DecipherSymbol(StringReader stringReader) {
		pushbackReader = new PushbackReader(stringReader);
		
	}
	
	public Symbol next() throws IOException, CustomException {
		Symbol result = null;
		int character = pushbackReader.read();
		
		isLeftSquareBracket(character, result);
		isLeftCurlyBracket(character, result);
		isRightSquareBracket(character, result);
		isRightCurlyBracket(character, result);

		return result;
	}
	
//	public Symbol isLastCharacter() throws IOException, CustomException {
//		
//	}
	
	public void isLeftSquareBracket(int character, Symbol result) throws IOException, CustomException {
		if ( character == '[' ) {
			result = new Symbol(Symbol.Type.LEFT_SQUARE_BRACKET);
		}
	}
	
	public void isLeftCurlyBracket(int character, Symbol result) throws IOException, CustomException {
		if ( character == '{' ) {
			result = new Symbol(Symbol.Type.LEFT_CURLY_BRACKET);
		}
	}
	
	public void isRightSquareBracket(int character, Symbol result) throws IOException, CustomException {
		if ( character == ']' ) {
			result = new Symbol(Symbol.Type.RIGHT_SQUARE_BRACKET);
		}
	}
	
	public void isRightCurlyBracket(int character, Symbol result) throws IOException, CustomException {
		if ( character == '}' ) {
			result = new Symbol(Symbol.Type.RIGHT_CURLY_BRACKET);
		}
	}
}