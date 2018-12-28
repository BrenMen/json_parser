package uk.ac.uos.assignment;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.io.StringReader;

public class JSONParser {

	// Including my Analyser class so I can use it's code here.
	JSONAnalyser Analyser;

	// Including my Object class so I can initialise the JSON objects here.
	public JSONObject mainObject;

	/**
	 * Main parse method that will run the Parser.
	 * @param Text
	 * @throws CustomException
	 * @throws IOException
	 */
	public void parse(String Text) throws CustomException, IOException {
		// Collecting the first symbol
		Analyser = new JSONAnalyser(new StringReader(Text));
		JSONSymbol thisSymbol = Analyser.next();	
		// Converting JSON 'Text' into its relative symbol types.
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		JSONSymbol invalid = null;
		while (thisSymbol != invalid) {
			symbolsList.add(thisSymbol);
			thisSymbol = Analyser.next();
		}
		// Removing whitespace.
		ArrayList<JSONSymbol> noWhitespaceList = new ArrayList<JSONSymbol>();
		for (int i = 0; i < symbolsList.size(); i++) {
			JSONSymbol currentSymbol = symbolsList.get(i);
			if (JSONSymbol.Type.SPACE != currentSymbol.type) {
				noWhitespaceList.add(currentSymbol);
			}
		}
		symbolsList = noWhitespaceList;
		mainObject = parseObject(symbolsList);
	}

	/**
	 * Parsing all Null Symbol Types.
	 * @param input
	 * @return null
	 * @throws CustomException
	 */
	public Object parseNull(JSONSymbol input) throws CustomException {
		if (JSONSymbol.Type.NULL_BOOLEAN == input.type) {
			return null;
		} else {
			throw new CustomException("Error! This is an invalid Null value.");
		}
	}

	/**
	 * Parsing all Boolean Symbol Types.
	 * @param input
	 * @return boolean
	 * @throws CustomException
	 */
	public boolean parseBoolean(JSONSymbol input) throws CustomException {
		if (JSONSymbol.Type.TRUE_BOOLEAN == input.type) {
			return true;
		} else if (JSONSymbol.Type.FALSE_BOOLEAN == input.type) {
			return false;
		} else {
			throw new CustomException("Error! This is an invalid Boolean value.");
		}
	}

	/**
	 * Parsing all Number Symbol Types.
	 * @param input
	 * @return number
	 * @throws CustomException
	 */
	public Number parseNumber(JSONSymbol input) throws CustomException {
		Number result = null;
		if (JSONSymbol.Type.NUMBER == input.type) {
			if (input.value.contains(".")) {
				result = Double.parseDouble(input.value);
			} else {
				result =  Long.parseLong(input.value);
			}
		} else {
			throw new CustomException("Error! This is an invalid Number value.");
		}
		return result;
	}

	/**
	 * Parsing all String Symbol Types.
	 * @param input
	 * @return string
	 * @throws CustomException
	 */
	public String parseString(JSONSymbol input) throws CustomException {
		String stringResult = null;
		if (JSONSymbol.Type.STRING != input.type) {
			throw new CustomException("Error! This is an invalid String.");
		} else {
			stringResult = input.value;
		}
		return stringResult;
	}

	/**
	 * Parsing all Symbol Types that are within an Array.
	 * @param input
	 * @return JSONArray
	 * @throws CustomException
	 */
	public JSONArray parseArray(ArrayList<JSONSymbol> input) throws CustomException {
		ArrayList<Object> arrayValueList = new ArrayList<Object>();
		// Checking for valid Array.
		isValidArray(input);
		// Assigning Symbol types to each value in an Array.
		for (int i = 1; i < input.size() - 1; i = i + 2) {
			JSONSymbol thisSymbol = input.get(i);
			if (JSONSymbol.Type.NULL_BOOLEAN == thisSymbol.type) {
				arrayValueList.add(parseNull(thisSymbol));
			} else if (JSONSymbol.Type.TRUE_BOOLEAN == thisSymbol.type) {
				arrayValueList.add(parseBoolean(thisSymbol));
			} else if (JSONSymbol.Type.FALSE_BOOLEAN == thisSymbol.type) {
				arrayValueList.add(parseBoolean(thisSymbol));
			} else if (JSONSymbol.Type.NUMBER == thisSymbol.type) {
				arrayValueList.add(parseNumber(thisSymbol));
			} else if (JSONSymbol.Type.STRING == thisSymbol.type) {
				arrayValueList.add(parseString(thisSymbol));
				// Checking for an embedded Object in the Array.
			} else if (JSONSymbol.Type.RIGHT_CURLY_BRACKET == thisSymbol.type) {
				ArrayList<JSONSymbol> objectSymbolsArray = new ArrayList<JSONSymbol>();
				while (isEmbedded(JSONSymbol.Type.RIGHT_CURLY_BRACKET, thisSymbol.type)) {
					i++;
					objectSymbolsArray.add(thisSymbol);
					thisSymbol = input.get(i);
				}
				objectSymbolsArray.add(thisSymbol);
				arrayValueList.add(parseObject(objectSymbolsArray));
				// Checking for an embedded Array in the Array.
			} else if (JSONSymbol.Type.LEFT_SQUARE_BRACKET == thisSymbol.type) {
				ArrayList<JSONSymbol> symbolsArray = new ArrayList<JSONSymbol>();
				while (isEmbedded(JSONSymbol.Type.RIGHT_SQUARE_BRACKET, thisSymbol.type)) {
					i++;
					symbolsArray.add(thisSymbol);
					thisSymbol = input.get(i);
				}
				symbolsArray.add(thisSymbol);
				arrayValueList.add(parseArray(symbolsArray));
			} else {
				throw new CustomException("Error! An Array cannot contain this value.");
			}
		}
		return new JSONArray(arrayValueList);
	}

	/**
	 * Parsing all Symbol Types that are within a Hash.
	 * @param input
	 * @return ArrayList
	 * @throws CustomException
	 */
	public ArrayList<Object> parseHash(ArrayList<JSONSymbol> input) throws CustomException {
		ArrayList<Object> hashValueList = new ArrayList<Object>();
		// Checking for valid Hash.
		isValidHash(input);
		// Assigning Symbol types to each value in an Array.
		hashValueList.add(input.get(0).value);
		for (int i = 2; i < input.size(); i++) {
			JSONSymbol thisSymbol = input.get(i);
			if (thisSymbol.type == JSONSymbol.Type.NULL_BOOLEAN) {
				hashValueList.add(parseNull(thisSymbol));
			} else if (thisSymbol.type == JSONSymbol.Type.TRUE_BOOLEAN) {
				hashValueList.add(parseBoolean(thisSymbol));
			} else if (thisSymbol.type == JSONSymbol.Type.FALSE_BOOLEAN) {
				hashValueList.add(parseBoolean(thisSymbol));
			} else if (thisSymbol.type == JSONSymbol.Type.NUMBER) {
				hashValueList.add(parseNumber(thisSymbol));
			} else if (thisSymbol.type == JSONSymbol.Type.STRING) {
				hashValueList.add(parseString(thisSymbol));
				// Checking for an embedded Object in the Hash.
			} else if (thisSymbol.type == JSONSymbol.Type.LEFT_CURLY_BRACKET) {
				ArrayList<JSONSymbol> objectSymbolsArray = new ArrayList<JSONSymbol>();
				while (isEmbedded(JSONSymbol.Type.RIGHT_CURLY_BRACKET, thisSymbol.type)) {
					i++;
					objectSymbolsArray.add(thisSymbol);
					thisSymbol = input.get(i);
				}
				objectSymbolsArray.add(thisSymbol);
				hashValueList.add(parseObject(objectSymbolsArray));
				// Checking for an embedded Array in the Hash.
			} else if (thisSymbol.type == JSONSymbol.Type.LEFT_SQUARE_BRACKET) {
				ArrayList<JSONSymbol> symbolsArray = new ArrayList<JSONSymbol>();
				while (isEmbedded(JSONSymbol.Type.RIGHT_SQUARE_BRACKET, thisSymbol.type)) {
					i++;
					symbolsArray.add(thisSymbol);
					thisSymbol = input.get(i);
				}
				symbolsArray.add(thisSymbol);
				hashValueList.add(parseArray(symbolsArray));
			} else {
				throw new CustomException("Error! A Hash cannot contain this value.");
			}
		}
		return hashValueList;
	}

	/**
	 * Parsing all Symbol Types that are within an Object.
	 * @param input
	 * @return HashMap
	 * @throws CustomException
	 */
	public JSONObject parseObject(ArrayList<JSONSymbol> input) throws CustomException {
		HashMap<String, Object> jsonObject = new HashMap<String, Object>();
		// Checking for valid Object.
		isValidObject(input);
		// Assigning Key, Colon and Value depending on their position in the Object.
		if (input.size() >= 5) {
			for (int i = 1; i < input.size() - 3; i = i + 4) {
				// Collecting position of key.
				JSONSymbol thisKey = input.get(i);
				// Collecting position of Colon.
				JSONSymbol thisColon = input.get(i + 1);
				// Collecting position of Value
				JSONSymbol thisValue = input.get(i + 2);
				ArrayList<JSONSymbol> hashPair = new ArrayList<JSONSymbol>();
				hashPair.add(thisKey);
				hashPair.add(thisColon);
				// Checking for an embedded Object in the Object.
				if (JSONSymbol.Type.LEFT_CURLY_BRACKET == thisValue.type) {
					while (isEmbedded(JSONSymbol.Type.RIGHT_CURLY_BRACKET, thisValue.type)) {
						i++;
						hashPair.add(thisValue);
						thisValue = input.get(i + 2);
					}
					hashPair.add(thisValue);
					// Checking for an embedded Array in the Object.
				} else if (JSONSymbol.Type.LEFT_SQUARE_BRACKET == thisValue.type) {
					while (isEmbedded(JSONSymbol.Type.RIGHT_SQUARE_BRACKET, thisValue.type)) {
						i++;
						hashPair.add(thisValue);
						thisValue = input.get(i + 2);
					}
					hashPair.add(thisValue);
				} else {
					hashPair.add(thisValue);
				}
				ArrayList<Object> hashParsed = parseHash(hashPair);
				jsonObject.put((String) hashParsed.get(0), hashParsed.get(1));
			}
		}
		return new JSONObject(jsonObject);
	}

	public void isValidArray(ArrayList<JSONSymbol> input) throws CustomException {
		if (input.size() <= 1 || JSONSymbol.Type.LEFT_SQUARE_BRACKET != input.get(0).type
				|| JSONSymbol.Type.RIGHT_SQUARE_BRACKET != input.get(input.size() - 1).type) {
			throw new CustomException("Error! This is an invalid Array.");
		}
	}

	public void isValidObject(ArrayList<JSONSymbol> input) throws CustomException {
		if (input.size() <= 1 || JSONSymbol.Type.LEFT_CURLY_BRACKET != input.get(0).type
				|| JSONSymbol.Type.RIGHT_CURLY_BRACKET != input.get(input.size() - 1).type) {
			throw new CustomException("Error! This is an invalid Object.");
		}
	}

	public void isValidHash(ArrayList<JSONSymbol> input) throws CustomException {
		if (input.size() <= 2 || JSONSymbol.Type.STRING != input.get(0).type
				|| JSONSymbol.Type.COLON != input.get(1).type) {
			throw new CustomException("Error! This is an invalid Hash.");
		}
	}
	
	public boolean isEmbedded(JSONSymbol.Type thisBracket, JSONSymbol.Type thisValue) throws CustomException {
		boolean result = thisBracket != thisValue ? true : false;
		return result;
	}
}