package uk.ac.uos.assignment;

//Class sets all the JSON symbols needed
public class SymbolSetter {
	
	//Defining the enum type and the value to store text
		public final Type type;
		public final String value;
	
	//enum defines the necessary types ready to compare the
	//string formatted JSON in the LexicalConverter
	public enum Type {COMMA, COLON, STRING, INTEGER, TRUE_BOOLEAN,
		FALSE_BOOLEAN, NULL_BOOLEAN, LEFT_SQUARE_BRACKET, RIGHT_SQUARE_BRACKET,
		LEFT_CURLY_BRACKET, RIGHT_CURLY_BRACKET, SPACE};
	
	//Constructs the JSON symbol with a type
	public SymbolSetter(Type type) {
		this(type, null);
	}
	
	//Constructs the JSON symbol with both the type and value
	public SymbolSetter(Type type, String value) {
		this.type = type;
		this.value = value;
	}
}