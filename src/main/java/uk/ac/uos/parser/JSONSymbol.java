package uk.ac.uos.parser;

//Class sets all the JSON symbols needed
public class JSONSymbol {
	
	//Defining the enum type and the value to store text
		public final Type type;
		public final String value;
	
	//enum defines the necessary types ready to compare the
	//string formatted JSON in the LexicalConverter
	public enum Type {COMMA, COLON, STRING, NUMBER, TRUE_BOOLEAN, FALSE_BOOLEAN,
		NULL_BOOLEAN, LEFT_SQUARE_BRACKET, RIGHT_SQUARE_BRACKET, LEFT_CURLY_BRACKET,
		RIGHT_CURLY_BRACKET, SPACE};
	
	//Constructs the JSON symbol with a type
	public JSONSymbol(Type type) {
		this(type, null);
	}
	
	//Constructs the JSON symbol with both the type and value
	public JSONSymbol(Type type, String value) {
		this.type = type;
		this.value = value;
	}
}