package uk.ac.uos.assignment;

import java.io.StringReader;
import java.io.PushbackReader;
import java.io.IOException;


//Class reads through JSON and deciphers the symbol types
//of the JSON syntax
public class SymbolDecipher {
	
	//PushbackReader allowed me to move my position in the 
	//JSON back whenever I decided to read ahead more than one
	//character inside the JSON
	private PushbackReader pushbackReader;
	
	//Constructor that takes StringReader as its argument
	public SymbolDecipher(StringReader reader) {
		pushbackReader = new PushbackReader(reader);
	}
	
	public Symbol next() throws IOException, JSONException {
		
	}
}