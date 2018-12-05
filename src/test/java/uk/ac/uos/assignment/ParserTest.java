package uk.ac.uos.assignment;

import static org.junit.Assert.*;

import java.io.IOException;
// import java.util.ArrayList;
// import java.util.HashMap;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import uk.ac.uos.assignment.JSONParser;

public class ParserTest {
	
	JSONParser Parser;
	
	@Before
	public void setup(){
		Parser = new JSONParser();
	}
	
	
	@Test
	public void correctString() throws IOException, CustomException {
		JSONSymbol symbol = new JSONSymbol(JSONSymbol.Type.STRING, "Scooby Dooby Doo");
		String result = Parser.parseString(symbol);
		assertEquals("Scooby Dooby Doo", result);
	}
	
	@Test
	public void parsingAnObjectWithATrueValue() throws CustomException {
		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.STRING, "NICE KEY");
		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbol4 = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		JSONSymbol symbol5 = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
		listOfSymbols.add(symbol1);
		listOfSymbols.add(symbol2);
		listOfSymbols.add(symbol3);
		listOfSymbols.add(symbol4);
		listOfSymbols.add(symbol5);
		
		JSONObject jsonObject = Parser.parseObject(listOfSymbols);
		assertEquals(1, jsonObject.JSONObjectContent.size());
		assertEquals(true, jsonObject.JSONObjectContent.get("NICE KEY"));
	}
}
	