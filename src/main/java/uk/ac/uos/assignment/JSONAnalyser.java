package uk.ac.uos.assignment;

import java.io.StringReader;
import java.io.PushbackReader;
import java.io.IOException;

// Class reads through JSON and deciphers the symbol types
// of the JSON syntax
public class JSONAnalyser {

	// PushbackReader used to move my position forward and
	// back whenever I decided to read ahead more than one
	// character in the JSON
	private PushbackReader reader;

	/**
	 * Constructor that takes StringReader as its argument
	 * 
	 * @param inputString
	 */
	public JSONAnalyser(StringReader inputString) {
		reader = new PushbackReader(inputString);

	}

	/**
	 * Main method of this class. Will read through all characters.
	 * 
	 * @return result
	 * @throws IOException
	 * @throws CustomException
	 */
	public JSONSymbol next() throws IOException, CustomException {
		JSONSymbol result = null;
		int position = reader.read();
		// I used isWhitespace and pushbackReader to check
		// for consecutive whitespace and return to my initial
		// position once ended.
		if (-1 != position) {
			if (Character.isWhitespace(position)) {
				result = buildWhitespace(position, result);
			}
			// BuildString will check for characters between quotation
			// marks, and return that string as its symbol.
			else if (position == '"') {
				result = buildString(position, result);
			}
			// These builders will read through the JSON and check if the
			// characters spell the specific boolean; returning its symbol.
			else if (position == 'n') {
				result = buildNullBoolean(position, result);
			} else if (position == 't') {
				result = buildTrueBoolean(position, result);
			} else if (position == 'f') {
				result = buildFalseBoolean(position, result);
			}
			// This builder will read forward until an invalid character
			// is found; returning the valid number symbol. A number could
			// start with either a digit or a - for a negative number.
			else if (Character.isDigit(position) || position == '-') {
				result = buildNumber(position, result);
			}
			// Checking single characters to assign them to relative
			// symbols types.
			else if (position == '{') {
				result = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
			} else if (position == '}') {
				result = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
			} else if (position == '[') {
				result = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
			} else if (position == ']') {
				result = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
			} else if (position == ',') {
				result = new JSONSymbol(JSONSymbol.Type.COMMA);
			} else if (position == ':') {
				result = new JSONSymbol(JSONSymbol.Type.COLON);
			}
		}
		return result;
	}

	// Builder for whitespace
	public JSONSymbol buildWhitespace(int position, JSONSymbol result) throws IOException, CustomException {
		while (Character.isWhitespace(position)) {
			position = reader.read();
			result = new JSONSymbol(JSONSymbol.Type.SPACE);
			reader.unread(position);
		}
		return result;
	}

	// Builder for a string
	public JSONSymbol buildString(int position, JSONSymbol result) throws IOException, CustomException {
		String finalString = "";
		position = reader.read();
		while (position != '"') {
			finalString += (char) position;
			position = reader.read();
		}
		result = new JSONSymbol(JSONSymbol.Type.STRING, finalString);
		return result;

	}

	// Builder for a null boolean
	public JSONSymbol buildNullBoolean(int position, JSONSymbol result) throws IOException, CustomException {
		position = reader.read();
		if (position == 'u') {
			position = reader.read();
			if (position == 'l') {
				position = reader.read();
				if (position == 'l') {
					result = new JSONSymbol(JSONSymbol.Type.NULL_BOOLEAN);
				} else {
					throw new CustomException("Error! This is an invalid Null value.");
				}
			} else {
				throw new CustomException("Error! This is an invalid Null value.");
			}
		} else {
			throw new CustomException("Error! This is an invalid Null value.");
		}
		return result;
	}

	// Builder for a true boolean
	public JSONSymbol buildTrueBoolean(int position, JSONSymbol result) throws IOException, CustomException {
		position = reader.read();
		if (position == 'r') {
			position = reader.read();
			if (position == 'u') {
				position = reader.read();
				if (position == 'e') {
					result = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
				} else {
					throw new CustomException("Error! This is an invalid True value.");
				}
			} else {
				throw new CustomException("Error! This is an invalid True value.");
			}
		} else {
			throw new CustomException("Error! This is an invalid True value.");
		}
		return result;
	}

	// Builder for a false boolean
	public JSONSymbol buildFalseBoolean(int position, JSONSymbol result) throws IOException, CustomException {
		position = reader.read();
		if (position == 'a') {
			position = reader.read();
			if (position == 'l') {
				position = reader.read();
				if (position == 's') {
					position = reader.read();
					if (position == 'e') {
						result = new JSONSymbol(JSONSymbol.Type.FALSE_BOOLEAN);
					} else {
						throw new CustomException("Error! This is an invalid False value.");
					}
				} else {
					throw new CustomException("Error! This is an invalid False value.");
				}
			} else {
				throw new CustomException("Error! This is an invalid False value.");
			}
		} else {
			throw new CustomException("Error! This is an invalid False value.");
		}
		return result;
	}

	// Builder for a number
	public JSONSymbol buildNumber(int position, JSONSymbol result) throws IOException, CustomException {
		String finalNumber = "";
		while (Character.isDigit(position) || position == 'e' || position == 'E' || position == '+' || position == '-'
				|| position == '.') {
			finalNumber += (char) position;
			position = reader.read();
		}
		result = new JSONSymbol(JSONSymbol.Type.NUMBER, finalNumber);
		reader.unread(position);
		return result;
	}
}