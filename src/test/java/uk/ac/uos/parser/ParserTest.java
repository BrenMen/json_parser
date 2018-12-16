package uk.ac.uos.parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import uk.ac.uos.parser.CustomException;
import uk.ac.uos.parser.JSONArray;
import uk.ac.uos.parser.JSONObject;
import uk.ac.uos.parser.JSONParser;
import uk.ac.uos.parser.JSONSymbol;

public class ParserTest {
	
	JSONParser Parser;
	
	@Before
	public void setup(){
		Parser = new JSONParser();
	}

	@Test
	public void parsingCorrectNullTest() throws CustomException {
		JSONSymbol symbol = new JSONSymbol(JSONSymbol.Type.NULL_BOOLEAN);
		Object result = Parser.parseNull(symbol);
		assertEquals(null, result);
	}

	@Test
	public void parsingIncorrectNullTest() throws IOException, CustomException {
		boolean exception = false;
		String customMessage = "";
		JSONSymbol symbol = new JSONSymbol(JSONSymbol.Type.NUMBER);
		try {
			Parser.parseNull(symbol); 
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid Null value.", customMessage);
	}

	@Test
	public void parsingCorrectTrueBooleanTest() throws CustomException {
		JSONSymbol symbol = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		boolean result = Parser.parseBoolean(symbol);
		assertEquals(true, result);
	}
	
	@Test
	public void parsingIncorrectTrueBooleanTest() throws IOException, CustomException {
		boolean exception = false;
		String customMessage = "";
		JSONSymbol symbol = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		try {
			Parser.parseBoolean(symbol); 
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid Boolean value.", customMessage);
	}
	
	@Test
	public void parsingCorrectDoubleNumberTest() throws CustomException {
		JSONSymbol symbol = new JSONSymbol(JSONSymbol.Type.NUMBER, "625.114");
		double result = (double) Parser.parseNumber(symbol);
		double expected = 625.114;
		assertEquals(expected, result, 0);
	}
	
	@Test
	public void parsingCorrectLongNumberTest() throws CustomException {
		JSONSymbol symbol = new JSONSymbol(JSONSymbol.Type.NUMBER, "6392");
		long result = (long) Parser.parseNumber(symbol);
		assertEquals(6392, result);
	}
	
	@Test
	public void parsingIncorrectNumberTest() throws IOException, CustomException {
		boolean exception = false;
		String customMessage = "";
		JSONSymbol symbol = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		try {
			Parser.parseNumber(symbol); 
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid Number value.", customMessage);
	}

	@Test
	public void parsingCorrectString() throws IOException, CustomException {
		JSONSymbol symbol = new JSONSymbol(JSONSymbol.Type.STRING, "This is a string");
		String result = Parser.parseString(symbol);
		assertEquals("This is a string", result);
	}
	
	@Test
	public void parsingIncorrectStringTest() throws IOException, CustomException {
		boolean exception = false;
		String customMessage = "";
		JSONSymbol symbol = new JSONSymbol(JSONSymbol.Type.SPACE);
		try {
			Parser.parseString(symbol); 
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid String.", customMessage);
	}
	
	@Test
	public void parsingEmptyObjectTest() throws IOException, CustomException {
		Parser.Parse("{}");
		assertEquals(new HashMap<String,Object>(), Parser.mainObject.JSONObjectContents);
	}
	
	@Test
	public void parsingEmptyObjectWithNewlineTest() throws IOException, CustomException {
		Parser.Parse("{\n}");
		assertEquals(new HashMap<String,Object>(), Parser.mainObject.JSONObjectContents);
	}
	
	@Test
	public void parsingEmptyObjectWithTabTest() throws IOException, CustomException {
		Parser.Parse("{\t}");
		assertEquals(new HashMap<String,Object>(), Parser.mainObject.JSONObjectContents);
	}
	
	@Test
	public void parsingEmptyObjectWithSpaceTest() throws IOException, CustomException {
		Parser.Parse("{ }");
		assertEquals(new HashMap<String,Object>(), Parser.mainObject.JSONObjectContents);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void parsingCorrectEmptyArrayTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(0, firstArray.JSONArrayContents.size());
	}
	

	@Test
	public void parsingCorrectEmptyObjectTest() throws CustomException {
		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbol1);
		symbolsList.add(symbol2);
		
		JSONObject firstObject = Parser.parseObject(symbolsList);
		assertEquals(0, firstObject.JSONObjectContents.size());
	}
	
	@Test
	public void parsingCorrectArrayContainingArrayTest() throws CustomException { 
		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		JSONSymbol symbol4 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbol1);
		symbolsList.add(symbol2);
		symbolsList.add(symbol3);
		symbolsList.add(symbol4);
		
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(1, firstArray.JSONArrayContents.size());
		JSONArray secondArray = (JSONArray) firstArray.JSONArrayContents.get(0);
		assertEquals(0, secondArray.JSONArrayContents.size());
	}
	
	@Test
	public void parsingCorrectArrayContainingArrayWithTrueTest() throws CustomException { 
		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		JSONSymbol symbol4 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		JSONSymbol symbol5 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbol1);
		symbolsList.add(symbol2);
		symbolsList.add(symbol3);
		symbolsList.add(symbol4);
		symbolsList.add(symbol5);

		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(1, firstArray.JSONArrayContents.size());
		JSONArray secondArray = (JSONArray) firstArray.JSONArrayContents.get(0);
		assertEquals(1, secondArray.JSONArrayContents.size());
	}
	
	@Test
	public void parsingCorrectArrayContainingArrayWithTwoNumbersTest() throws CustomException { 
		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.NUMBER, "63749");
		JSONSymbol symbol4 = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbol5 = new JSONSymbol(JSONSymbol.Type.NUMBER, "637.49");
		JSONSymbol symbol6 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		JSONSymbol symbol7 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbol1);
		symbolsList.add(symbol2);
		symbolsList.add(symbol3);
		symbolsList.add(symbol4);
		symbolsList.add(symbol5);
		symbolsList.add(symbol6);
		symbolsList.add(symbol7);
		
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(1, firstArray.JSONArrayContents.size());
		JSONArray secondArray = (JSONArray) firstArray.JSONArrayContents.get(0);
		assertEquals(2, secondArray.JSONArrayContents.size());
	}
	
	@Test
	public void parsingCorrectArrayWithNullBooleanContainingArrayWithTwoBooleansTest() throws CustomException { 
		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		JSONSymbol symbol4 = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbol5 = new JSONSymbol(JSONSymbol.Type.FALSE_BOOLEAN);
		JSONSymbol symbol6 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		JSONSymbol symbol7 = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbol8 = new JSONSymbol(JSONSymbol.Type.NULL_BOOLEAN);
		JSONSymbol symbol9 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbol1);
		symbolsList.add(symbol2);
		symbolsList.add(symbol3);
		symbolsList.add(symbol4);
		symbolsList.add(symbol5);
		symbolsList.add(symbol6);
		symbolsList.add(symbol7);
		symbolsList.add(symbol8);
		symbolsList.add(symbol9);
		
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(2, firstArray.JSONArrayContents.size());
		JSONArray secondArray = (JSONArray) firstArray.JSONArrayContents.get(0);
		assertEquals(2, secondArray.JSONArrayContents.size());
		assertEquals(true, secondArray.JSONArrayContents.get(0));
		assertEquals(false, secondArray.JSONArrayContents.get(1));
		assertEquals(null, firstArray.JSONArrayContents.get(1));
	}
	
	
	
	@Test
	public void parsingCorrectArrayWithNullTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.NULL_BOOLEAN);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(1, firstArray.JSONArrayContents.size());
		assertEquals(null, firstArray.JSONArrayContents.get(0));
	}
	
	@Test
	public void parsingCorrectArrayWithTrueTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(1, firstArray.JSONArrayContents.size());
		assertEquals(true, firstArray.JSONArrayContents.get(0));
	}
	
	@Test
	public void parsingCorrectArrayWithFalseTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.FALSE_BOOLEAN);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(1, firstArray.JSONArrayContents.size());
		assertEquals(false, firstArray.JSONArrayContents.get(0));
	}
	
	@Test
	public void parsingCorrectArrayWithLongTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.NUMBER, "5239");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(1, firstArray.JSONArrayContents.size());
		assertEquals((long) 5239, firstArray.JSONArrayContents.get(0));
	}
	
	@Test
	public void parsingCorrectArrayWithDoubleTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.NUMBER, "52.39");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(1, firstArray.JSONArrayContents.size());
		assertEquals((double) 52.39, firstArray.JSONArrayContents.get(0));
	}
	
	@Test
	public void parsingCorrectArrayWithStringTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.STRING, "This is a String");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(1, firstArray.JSONArrayContents.size());
		assertEquals("This is a String", firstArray.JSONArrayContents.get(0));
	}
	
	@Test
	public void parsingCorrectArrayWithTwoSymbolTypesTest() throws CustomException {
		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.NUMBER, "54.267");
		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbol4 = new JSONSymbol(JSONSymbol.Type.STRING, "Hey, another String");
		JSONSymbol symbol5 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbol1);
		symbolsList.add(symbol2);
		symbolsList.add(symbol3);
		symbolsList.add(symbol4);
		symbolsList.add(symbol5);
		
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(2, firstArray.JSONArrayContents.size());
		assertEquals((double) 54.267, firstArray.JSONArrayContents.get(0));
		assertEquals("Hey, another String", firstArray.JSONArrayContents.get(1));
	}
	
	@Test
	public void parsingCorrectArrayWithAllSymbolTypesTest() throws CustomException {
		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.NULL_BOOLEAN);
		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbol4 = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		JSONSymbol symbol5 = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbol6 = new JSONSymbol(JSONSymbol.Type.FALSE_BOOLEAN);
		JSONSymbol symbol7 = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbol8 = new JSONSymbol(JSONSymbol.Type.NUMBER, "9872");
		JSONSymbol symbol9 = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbol10 = new JSONSymbol(JSONSymbol.Type.NUMBER, "9.872");
		JSONSymbol symbol11 = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbol12 = new JSONSymbol(JSONSymbol.Type.STRING, "This should be a String");
		JSONSymbol symbol13 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbol1);
		symbolsList.add(symbol2);
		symbolsList.add(symbol3);
		symbolsList.add(symbol4);
		symbolsList.add(symbol5);
		symbolsList.add(symbol6);
		symbolsList.add(symbol7);
		symbolsList.add(symbol8);
		symbolsList.add(symbol9);
		symbolsList.add(symbol10);
		symbolsList.add(symbol11);
		symbolsList.add(symbol12);
		symbolsList.add(symbol13);
		
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(6, firstArray.JSONArrayContents.size());
		assertEquals(null, firstArray.JSONArrayContents.get(0));
		assertEquals(true, firstArray.JSONArrayContents.get(1));
		assertEquals(false, firstArray.JSONArrayContents.get(2));
		assertEquals((long) 9872, firstArray.JSONArrayContents.get(3));
		assertEquals(9.872, firstArray.JSONArrayContents.get(4));
		assertEquals("This should be a String", firstArray.JSONArrayContents.get(5));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void parsingIncorrectEmptyArrayTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		boolean exception = false;
		String customMessage = "";
		try {
			Parser.parseArray(symbolsList);
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid Array.", customMessage);
	}
	
	
	
	@Test
	public void parsingIncorrectArrayWithoutCommaTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.STRING, "First value");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.STRING, "Second value");
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		boolean exception = false;
		String customMessage = "";
		try {
			Parser.parseArray(symbolsList);
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! Missing comma value in Array.", customMessage);
	}
	
	@Test
	public void parsingIncorrectArrayTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		
		boolean exception = false;
		String customMessage = "";
		
		try {
			Parser.parseArray(symbolsList);
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		
		assertTrue(exception);
		assertEquals("Error! This is an invalid Array.", customMessage);
	}

	
	
	
	
	
	
	
	
	
	
	@Test
	public void parsingCorrectObjectWithNullBooleanTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.STRING, "Is it a bird? Is it a String?");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.NULL_BOOLEAN);
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);
		
		JSONObject firstObject = Parser.parseObject(symbolsList);
		assertEquals(1, firstObject.JSONObjectContents.size());
		assertEquals(null, firstObject.JSONObjectContents.get(""));
	}
	
	@Test
	public void parsingCorrectObjectWithTrueBooleanTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.STRING, "Hey look, a String");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);
		
		JSONObject firstObject = Parser.parseObject(symbolsList);
		assertEquals(1, firstObject.JSONObjectContents.size());
		assertEquals(true, firstObject.JSONObjectContents.get("Hey look, a String"));
	}

	@Test
	public void parsingCorrectObjectWithStringTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.STRING, "Ohhhweee, A brand new String!");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.STRING, "I wanna be a String too!");
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);
		
		JSONObject firstObject = Parser.parseObject(symbolsList);
		assertEquals(1, firstObject.JSONObjectContents.size());
		assertEquals("I wanna be a String too!", firstObject.JSONObjectContents.get("Ohhhweee, A brand new String!"));
	}

	@Test
	public void parsingCorrectObjectWithLongtest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.STRING, "A String? Well would you look at that!");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.NUMBER, "3574");
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);
		
		JSONObject firstObject = Parser.parseObject(symbolsList);
		assertEquals(1, firstObject.JSONObjectContents.size());
		assertEquals((long) 3574, firstObject.JSONObjectContents.get("A String? Well would you look at that!"));
	}
	
	@Test
	public void parsingCorrectObjectWithDoubletest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.STRING, "Another String? Well would you look at that!");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.NUMBER, "3.574");
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);
		
		JSONObject firstObject = Parser.parseObject(symbolsList);
		assertEquals(1, firstObject.JSONObjectContents.size());
		assertEquals((double) 3.574, firstObject.JSONObjectContents.get("Another String? Well would you look at that!"));
	}
	
	@Test
	public void parsingCorrectObjectWithArrayTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.STRING, "One String coming up!");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		JSONSymbol symbolSix = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);
		symbolsList.add(symbolSix);
		
		JSONObject firstObject = Parser.parseObject(symbolsList);
		assertEquals(1, firstObject.JSONObjectContents.size());
		JSONArray secondArray = (JSONArray) firstObject.JSONObjectContents.get("One String coming up!");
		assertEquals(0, secondArray.JSONArrayContents.size());
	}
	
	@Test
	public void parsingCorrectObjectWithArrayWithNumberTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.STRING, "Another String coming up!");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.NUMBER, "734.727");
		JSONSymbol symbolSix = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		JSONSymbol symbolSeven = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);
		symbolsList.add(symbolSix);
		symbolsList.add(symbolSeven);
		
		JSONObject firstObject = Parser.parseObject(symbolsList);
		assertEquals(1, firstObject.JSONObjectContents.size());
		JSONArray secondArray = (JSONArray) firstObject.JSONObjectContents.get("Another String coming up!");
		assertEquals(1, secondArray.JSONArrayContents.size());
		assertEquals((double) 734.727, secondArray.JSONArrayContents.get(0));
	}
	
	@Test
	public void parsingObjectWithObjectTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.STRING, "Time to try this String");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		JSONSymbol symbolSix = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);
		symbolsList.add(symbolSix);
		
		JSONObject firstObject = Parser.parseObject(symbolsList);
		assertEquals(1, firstObject.JSONObjectContents.size());
		JSONObject secondObject = (JSONObject) firstObject.JSONObjectContents.get("Time to try this String");
		assertEquals(0, secondObject.JSONObjectContents.size());
	}
	
	@Test
	public void parsingCorrectObjectWithObjectAndFalseBooleanTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.STRING, "Uno, Duno, String");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.STRING, "Tres, Quatre, String");
		JSONSymbol symbolSix = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolSeven = new JSONSymbol(JSONSymbol.Type.FALSE_BOOLEAN);
		JSONSymbol symbolEight = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		JSONSymbol symbolNine = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);
		symbolsList.add(symbolSix);
		symbolsList.add(symbolSeven);
		symbolsList.add(symbolEight);
		symbolsList.add(symbolNine);
		
		JSONObject firstObject = Parser.parseObject(symbolsList);
		assertEquals(1, firstObject.JSONObjectContents.size());
		JSONObject secondObject = (JSONObject) firstObject.JSONObjectContents.get("Uno, Duno, String");
		assertEquals(1, secondObject.JSONObjectContents.size());
		assertEquals(false, secondObject.JSONObjectContents.get("Tres, Quatre, String"));
	}
	
//	@Test
//	public void parsingAnArrayWithAnObjectInIt() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
//		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.STRING, "false");
//		JSONSymbol symbol4 = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbol5 = new JSONSymbol(JSONSymbol.Type.FALSE_BOOLEAN);
//		JSONSymbol symbol6 = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
//		JSONSymbol symbol7 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		listOfSymbols.add(symbol2);
//		listOfSymbols.add(symbol3);
//		listOfSymbols.add(symbol4);
//		listOfSymbols.add(symbol5);
//		listOfSymbols.add(symbol6);
//		listOfSymbols.add(symbol7);
//		
//		JSONArray jsonArray = Parser.parseArray(listOfSymbols);
//		assertEquals(1, jsonArray.JSONArrayContents.size());
//		JSONObject jsonObject = (JSONObject) jsonArray.JSONArrayContents.get(0);
//		assertEquals(1, jsonObject.JSONObjectContents.size());
//		assertEquals(false, jsonObject.JSONObjectContents.get("false"));
//	}
//	
//	@Test
//	public void parsingAnObjectWithATwoStrings() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.STRING, "Name");
//		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbol4 = new JSONSymbol(JSONSymbol.Type.STRING, "Alistair");
//		JSONSymbol symbol5 = new JSONSymbol(JSONSymbol.Type.COMMA);
//		JSONSymbol symbol6 = new JSONSymbol(JSONSymbol.Type.STRING, "Age");
//		JSONSymbol symbol7 = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbol8 = new JSONSymbol(JSONSymbol.Type.STRING, "20");
//		JSONSymbol symbol9 = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		listOfSymbols.add(symbol2);
//		listOfSymbols.add(symbol3);
//		listOfSymbols.add(symbol4);
//		listOfSymbols.add(symbol5);
//		listOfSymbols.add(symbol6);
//		listOfSymbols.add(symbol7);
//		listOfSymbols.add(symbol8);
//		listOfSymbols.add(symbol9);
//		
//		JSONObject jsonObject = Parser.parseObject(listOfSymbols);
//		assertEquals(2, jsonObject.JSONObjectContents.size());
//		assertEquals("Alistair", jsonObject.JSONObjectContents.get("Name"));
//		assertEquals("20", jsonObject.JSONObjectContents.get("Age"));
//	}
//	
//	
//	
//	
//	
//	@Test
//	public void parsingIncorrectEmptyObjectTest() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
//		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
//		symbolsList.add(symbol1);
//		symbolsList.add(symbol2);
//		
//		boolean exception = false;
//		String customMessage = "";
//		
//		try {
//			Parser.parseObject(symbolsList);
//		} catch (CustomException error) {
//			exception = true;
//			customMessage = error.getMessage();
//		}
//		
//		assertTrue(exception);
//		assertEquals("Error! This is an invalid Object.",customMessage);
//	}
//
//	@Test
//	public void parsingIncorrectObjectWithoutCommaTest() throws CustomException {
//		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
//		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.NUMBER, "7656");
//		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.NUMBER, "76.56");
//		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.STRING, "Good");
//		JSONSymbol symbolSix = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbolSeven = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
//		JSONSymbol symbolEight = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
//		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
//		symbolsList.add(symbolOne);
//		symbolsList.add(symbolTwo);
//		symbolsList.add(symbolThree);
//		symbolsList.add(symbolFour);
//		symbolsList.add(symbolFive);
//		symbolsList.add(symbolSix);
//		symbolsList.add(symbolSeven);
//		symbolsList.add(symbolEight);
//		
//		boolean exception = false;
//		String customMessage = "";
//		try {
//			Parser.parseObject(symbolsList);
//		} catch (CustomException error) {
//			exception = true;
//			customMessage = error.getMessage();
//		}
//		assertTrue(exception);
//		assertEquals("Error! Missing comma value in Object.", customMessage);
//	}
//	
//	@Test
//	public void parsingIncorrectObjectTest() throws CustomException {
//		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
//		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
//		symbolsList.add(symbolOne);
//		
//		boolean exception = false;
//		String customMessage = "";
//		
//		try {
//			Parser.parseObject(symbolsList);
//		} catch (CustomException error) {
//			exception = true;
//			customMessage = error.getMessage();
//		}
//		
//		assertTrue(exception);
//		assertEquals("Error! This is an invalid Object.", customMessage);
//	}
//	
//
//	// work through these below:
//	
//
//	@Test
//	public void parsingATrueValuePairThatsTooShort() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.STRING, "KEY");
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		
//		boolean thrown = false;
//		String errorMessage = "";
//		
//		try {
//			Parser.parseHash(listOfSymbols);
//		} catch (CustomException error) {
//			thrown = true;
//			errorMessage = error.getMessage();
//		}
//		
//		assertTrue(thrown);
//		assertEquals("Error! This is an invalid Hash.",errorMessage);
//	}
//	
//	@Test
//	public void parsingATrueValuePairWithoutColon() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.STRING, "KEY");
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
//		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		listOfSymbols.add(symbol2);
//		listOfSymbols.add(symbol3);
//		
//		boolean thrown = false;
//		String errorMessage = "";
//		
//		try {
//			Parser.parseHash(listOfSymbols);
//		} catch (CustomException error) {
//			thrown = true;
//			errorMessage = error.getMessage();
//		}
//		
//		assertTrue(thrown);
//		assertEquals("Error! This is an invalid Hash.",errorMessage);
//	}
//	
//	@Test
//	public void parsingAPairThatHasAnIncorrectValue() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.STRING, "KEY");
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.SPACE);
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		listOfSymbols.add(symbol2);
//		listOfSymbols.add(symbol3);
//		
//		boolean thrown = false;
//		String errorMessage = "";
//		
//		try {
//			Parser.parseHash(listOfSymbols);
//		} catch (CustomException error) {
//			thrown = true;
//			errorMessage = error.getMessage();
//		}
//		
//		assertTrue(thrown);
//		assertEquals("Error! An Object cannot contain this value.",errorMessage);
//	}
//	
//	@Test
//	public void parsingATrueValuePairWithoutStringThrowsException() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.NULL_BOOLEAN);
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		listOfSymbols.add(symbol2);
//		listOfSymbols.add(symbol3);
//		
//		boolean thrown = false;
//		String errorMessage = "";
//		
//		try {
//			Parser.parseHash(listOfSymbols);
//		} catch (CustomException error) {
//			thrown = true;
//			errorMessage = error.getMessage();
//		}
//		
//		assertTrue(thrown);
//		assertEquals("Error! This is an invalid Hash.",errorMessage);
//	}
//	
//	@Test
//	public void parsingATrueValuePair() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.STRING, "KEY");
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		listOfSymbols.add(symbol2);
//		listOfSymbols.add(symbol3);
//		
//		ArrayList<Object> keyValuePair = Parser.parseHash(listOfSymbols);
//		assertEquals(2, keyValuePair.size());
//		assertEquals("KEY", keyValuePair.get(0));
//		assertEquals(true, keyValuePair.get(1));
//	}
//	
//	@Test
//	public void parsingAFalseValuePair() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.STRING, "KEY");
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.FALSE_BOOLEAN);
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		listOfSymbols.add(symbol2);
//		listOfSymbols.add(symbol3);
//		
//		ArrayList<Object> keyValuePair = Parser.parseHash(listOfSymbols);
//		assertEquals(2, keyValuePair.size());
//		assertEquals("KEY", keyValuePair.get(0));
//		assertEquals(false, keyValuePair.get(1));
//	}
//	
//	@Test
//	public void parsingANullValuePair() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.STRING, "KEY");
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.NULL_BOOLEAN);
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		listOfSymbols.add(symbol2);
//		listOfSymbols.add(symbol3);
//		
//		ArrayList<Object> keyValuePair = Parser.parseHash(listOfSymbols);
//		assertEquals(2, keyValuePair.size());
//		assertEquals("KEY", keyValuePair.get(0));
//		assertEquals(null, keyValuePair.get(1));
//	}
//	
//	@Test
//	public void parsingAStringValuePair() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.STRING, "KEY");
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.STRING, "value");
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		listOfSymbols.add(symbol2);
//		listOfSymbols.add(symbol3);
//		
//		ArrayList<Object> keyValuePair = Parser.parseHash(listOfSymbols);
//		assertEquals(2, keyValuePair.size());
//		assertEquals("KEY", keyValuePair.get(0));
//		assertEquals("value", keyValuePair.get(1));
//	}
//	
//	@Test
//	public void parsingALongValuePair() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.STRING, "KEY");
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.NUMBER, "10661066");
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		listOfSymbols.add(symbol2);
//		listOfSymbols.add(symbol3);
//		
//		ArrayList<Object> keyValuePair = Parser.parseHash(listOfSymbols);
//		assertEquals(2, keyValuePair.size());
//		assertEquals("KEY", keyValuePair.get(0));
//		assertEquals((long) 10661066, keyValuePair.get(1));
//	}
//	
//	@Test
//	public void parsingADoubleValuePair() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.STRING, "KEY");
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.NUMBER, "1066.1066");
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		listOfSymbols.add(symbol2);
//		listOfSymbols.add(symbol3);
//		
//		ArrayList<Object> keyValuePair = Parser.parseHash(listOfSymbols);
//		assertEquals(2, keyValuePair.size());
//		assertEquals("KEY", keyValuePair.get(0));
//		assertEquals((double) 1066.1066, keyValuePair.get(1));
//	}
//	
//	@Test
//	public void parsingAnEmptyArrayValuePair() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.STRING, "KEY");
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
//		JSONSymbol symbol4 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		listOfSymbols.add(symbol2);
//		listOfSymbols.add(symbol3);
//		listOfSymbols.add(symbol4);
//		
//		ArrayList<Object> keyValuePair = Parser.parseHash(listOfSymbols);
//		assertEquals(2, keyValuePair.size());
//		assertEquals("KEY", keyValuePair.get(0));
//		JSONArray secondTierArray = (JSONArray) keyValuePair.get(1);
//		assertEquals(0, secondTierArray.JSONArrayContents.size());
//	}
//	
//	@Test
//	public void parsingAnArrayWithTrueValuePair() throws CustomException {
//		JSONSymbol symbol1 = new JSONSymbol(JSONSymbol.Type.STRING, "KEY");
//		JSONSymbol symbol2 = new JSONSymbol(JSONSymbol.Type.COLON);
//		JSONSymbol symbol3 = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
//		JSONSymbol symbol4 = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
//		JSONSymbol symbol5 = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
//		ArrayList<JSONSymbol> listOfSymbols = new ArrayList<JSONSymbol>();
//		listOfSymbols.add(symbol1);
//		listOfSymbols.add(symbol2);
//		listOfSymbols.add(symbol3);
//		listOfSymbols.add(symbol4);
//		listOfSymbols.add(symbol5);
//		
//		ArrayList<Object> keyValuePair = Parser.parseHash(listOfSymbols);
//		assertEquals(2, keyValuePair.size());
//		assertEquals("KEY", keyValuePair.get(0));
//		JSONArray secondTierArray = (JSONArray) keyValuePair.get(1);
//		assertEquals(1, secondTierArray.JSONArrayContents.size());
//		assertEquals(true, secondTierArray.JSONArrayContents.get(0));
//	}
	

}
	