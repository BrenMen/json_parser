package uk.ac.uos.assignment;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
// import java.util.HashMap;

public class JSONParser {

	// Including my Analyser class so I can use it's code here.
	JSONAnalyser Analyser;

	// Including my Object class so I can initialise the JSON objects here.
	public JSONObject mainObject;

	public void parse(String Text) throws IOException, CustomException {
		// Processing the JSON 'Text' through a reader so it can be analysed.
		Analyser = new JSONAnalyser(new StringReader(Text));

		// Collecting the first symbol
		JSONSymbol currentSymbol = Analyser.next();

		// Iterating through the JSON 'Text', converting it into relative symbol types.
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		while (currentSymbol != null) {
			symbolsList.add(currentSymbol);
			currentSymbol = Analyser.next();
		}

		// Removing all the whitespace from the JSON.
		symbolsList = removeWhiteSpace(symbolsList);

		// Parsing the JSON which is an object to begin with.
		mainObject = parseObject(symbolsList);
	}
	
	// Parsing a String Symbol Type.
	public String parseString(JSONSymbol input) throws CustomException {
		String stringResult = null;
		if (input.type == JSONSymbol.Type.STRING) {
			stringResult = input.value;
		} else {
			throw new CustomException("BOOM! Error. This is not a string.");
		}
		return stringResult;
	}

	// Parsing a Boolean Symbol Type.
	public boolean parseBoolean(JSONSymbol input) throws CustomException {
		if (input.type == JSONSymbol.Type.TRUE_BOOLEAN) {
			return true;
		} else if (input.type == JSONSymbol.Type.FALSE_BOOLEAN) {
			return false;
		} else {
			throw new CustomException("BOOM! Error. This is not a boolean value.");
		}
	}
	
	// Parsing a Null Symbol Type.
	public Object parseNull(JSONSymbol input) throws CustomException {
		if (input.type == JSONSymbol.Type.NULL_BOOLEAN) {
			return null;
		} else {
			throw new CustomException("BOOM! Error. This is not null.");
		}
	}
	
	public Number parseNumber(JSONSymbol input) throws CustomException {
	}
	
	public JSONArray parseArray(ArrayList<JSONSymbol> input) throws CustomException {
	}
	
	public JSONObject parseObject(ArrayList<JSONSymbol> input) throws CustomException {
	}
	
	public ArrayList<Object> parseKeyValuePair(ArrayList<JSONSymbol> input) throws CustomException{
	}

	public ArrayList<JSONSymbol> removeWhiteSpace(ArrayList<JSONSymbol> listOfSymbols) {
	}
}