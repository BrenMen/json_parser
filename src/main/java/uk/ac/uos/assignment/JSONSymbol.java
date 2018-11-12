package uk.ac.uos.assignment;

//This class sets all the JSON symbols needed
public class JSONSymbol {
	
	//This enum defines the necessary types ready to compare the
	//string formatted JSON in the LexicalConverter
	public enum Type {COMMA, COLON, STRING_VALUE, NUMBER_VALUE, TRUE_BOOLEAN_VALUE,
		FALSE_BOOLEAN_VALUE, NULL_BOOLEAN_VALUE, LEFT_SQUARE_BRACKET, LEFT_CURLY_BRACKET,
		RIGHT_SQUARE_BRACKET, RIGHT_CURLY_BRACKET, SPACE};

	//This defines the enum type and the value to store text
	public final Type type;
	public final String value;
	
	//This constructs the JSON symbol with a type
	public JSONSymbol(Type type) {
		this(type, null);
	}
	
	//This constructs the JSON symbol with both the type and value
	public JSONSymbol(Type type, String value) {
		this.type = type;
		this.value = value;
	}
}