package uk.ac.uos.assignment;

import java.io.StringReader;
import java.io.PushbackReader;
import java.io.IOException;

//Class reads through JSON and deciphers the symbol types
//of the JSON syntax
public class DecipherSymbol {

	// PushbackReader used to move my position forward and
	// back whenever I decided to read ahead more than one
	// character in the JSON
	private PushbackReader pushbackReader;

	// Constructor that takes StringReader as its argument
	public DecipherSymbol(StringReader stringReader) {
		pushbackReader = new PushbackReader(stringReader);

	}

	public Symbol next() throws IOException, CustomException {
		Symbol result = null;
		int position = pushbackReader.read();

		// Checking single characters to assign them to relative
		// symbols types. Using if statements because these characters
		// are relatively trivial to check.

		// To check for all types of whitespace I used isWhitespace and
		// pushbackReader to check for consecutive whitespace and return
		// to my initial position once ended.
		if ( -1 != position ) {
			if (Character.isWhitespace(position)) {
				result = isWhitespace(position, result);
			}
			else if ( position == '{' ) {
				result = new Symbol(Symbol.Type.LEFT_CURLY_BRACKET);
			}
			else if ( position == '}' ) {
				result = new Symbol(Symbol.Type.RIGHT_CURLY_BRACKET);
			}
			else if( position == '[' ) {
				result = new Symbol(Symbol.Type.LEFT_SQUARE_BRACKET);
			}
			else if ( position == ']' ) {
				result = new Symbol(Symbol.Type.RIGHT_SQUARE_BRACKET);
			}
			else if ( position == ',' ) {
				result = new Symbol(Symbol.Type.COMMA);
			}
			else if ( position == ':' ) {
				result = new Symbol(Symbol.Type.COLON);
			}
		}
		return result;
	}
	
	public Symbol isWhitespace(Integer position, Symbol result) throws IOException, CustomException {
		while (Character.isWhitespace(position)) {
			position = pushbackReader.read(); 
			result = new Symbol(Symbol.Type.SPACE);
			pushbackReader.unread(position);
		}
		return result;
	}
}