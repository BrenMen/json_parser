package uk.ac.uos.assignment;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;


import org.junit.Before;
import org.junit.Test;

import uk.ac.uos.assignment.CustomException;
import uk.ac.uos.assignment.JSONArray;
import uk.ac.uos.assignment.JSONObject;
import uk.ac.uos.assignment.JSONParser;
import uk.ac.uos.assignment.JSONSymbol;

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
		Parser.parse("{}");
		assertEquals(new HashMap<String,Object>(), Parser.mainObject.thisObjectContents);
	}
	
	@Test
	public void parsingEmptyObjectWithNewlineTest() throws IOException, CustomException {
		Parser.parse("{\n}");
		assertEquals(new HashMap<String,Object>(), Parser.mainObject.thisObjectContents);
	}
	
	@Test
	public void parsingEmptyObjectWithTabTest() throws IOException, CustomException {
		Parser.parse("{\t}");
		assertEquals(new HashMap<String,Object>(), Parser.mainObject.thisObjectContents);
	}
	
	@Test
	public void parsingEmptyObjectWithSpaceTest() throws IOException, CustomException {
		Parser.parse("{ }");
		assertEquals(new HashMap<String,Object>(), Parser.mainObject.thisObjectContents);
	}
	
	@Test
	public void parsingCorrectEmptyArrayTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(0, firstArray.thisArrayContents.size());
	}

	@Test
	public void parsingCorrectEmptyObjectTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		
		JSONObject firstObject = Parser.parseObject(symbolsList);
		assertEquals(0, firstObject.thisObjectContents.size());
	}
	
	@Test
	public void parsingCorrectArrayContainingArrayTest() throws CustomException { 
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(1, firstArray.thisArrayContents.size());
		JSONArray secondArray = (JSONArray) firstArray.thisArrayContents.get(0);
		assertEquals(0, secondArray.thisArrayContents.size());
	}
	
	@Test
	public void parsingCorrectArrayWithObjectTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.STRING, "Oh lookie there, a String");
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.FALSE_BOOLEAN);
		JSONSymbol symbolSix = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		JSONSymbol symbolSeven = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);
		symbolsList.add(symbolSix);
		symbolsList.add(symbolSeven);
		
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(1, firstArray.thisArrayContents.size());
		JSONObject firstObject = (JSONObject) firstArray.thisArrayContents.get(0);
		assertEquals(1, firstObject.thisObjectContents.size());
		assertEquals(false, firstObject.thisObjectContents.get("Oh lookie there, a String"));
	}
	
	@Test
	public void parsingCorrectArrayContainingArrayWithTrueTest() throws CustomException { 
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);

		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(1, firstArray.thisArrayContents.size());
		JSONArray secondArray = (JSONArray) firstArray.thisArrayContents.get(0);
		assertEquals(1, secondArray.thisArrayContents.size());
	}
	
	@Test
	public void parsingCorrectArrayContainingArrayWithTwoNumbersTest() throws CustomException { 
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.NUMBER, "63749");
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.NUMBER, "637.49");
		JSONSymbol symbolSix = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		JSONSymbol symbolSeven = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);
		symbolsList.add(symbolSix);
		symbolsList.add(symbolSeven);
		
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(1, firstArray.thisArrayContents.size());
		JSONArray secondArray = (JSONArray) firstArray.thisArrayContents.get(0);
		assertEquals(2, secondArray.thisArrayContents.size());
	}
	
	@Test
	public void parsingCorrectArrayWithNullBooleanContainingArrayWithTwoBooleansTest() throws CustomException { 
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.FALSE_BOOLEAN);
		JSONSymbol symbolSix = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		JSONSymbol symbolSeven = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbolEight = new JSONSymbol(JSONSymbol.Type.NULL_BOOLEAN);
		JSONSymbol symbolNine = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
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
		
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(2, firstArray.thisArrayContents.size());
		JSONArray secondArray = (JSONArray) firstArray.thisArrayContents.get(0);
		assertEquals(2, secondArray.thisArrayContents.size());
		assertEquals(true, secondArray.thisArrayContents.get(0));
		assertEquals(false, secondArray.thisArrayContents.get(1));
		assertEquals(null, firstArray.thisArrayContents.get(1));
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
		assertEquals(1, firstArray.thisArrayContents.size());
		assertEquals(null, firstArray.thisArrayContents.get(0));
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
		assertEquals(1, firstArray.thisArrayContents.size());
		assertEquals(true, firstArray.thisArrayContents.get(0));
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
		assertEquals(1, firstArray.thisArrayContents.size());
		assertEquals(false, firstArray.thisArrayContents.get(0));
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
		assertEquals(1, firstArray.thisArrayContents.size());
		assertEquals((long) 5239, firstArray.thisArrayContents.get(0));
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
		assertEquals(1, firstArray.thisArrayContents.size());
		assertEquals((double) 52.39, firstArray.thisArrayContents.get(0));
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
		assertEquals(1, firstArray.thisArrayContents.size());
		assertEquals("This is a String", firstArray.thisArrayContents.get(0));
	}
	
	@Test
	public void parsingCorrectArrayWithTwoSymbolTypesTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.NUMBER, "54.267");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.STRING, "Hey, another String");
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);
		
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(2, firstArray.thisArrayContents.size());
		assertEquals((double) 54.267, firstArray.thisArrayContents.get(0));
		assertEquals("Hey, another String", firstArray.thisArrayContents.get(1));
	}
	
	@Test
	public void parsingCorrectArrayWithAllSymbolTypesTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_SQUARE_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.NULL_BOOLEAN);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbolSix = new JSONSymbol(JSONSymbol.Type.FALSE_BOOLEAN);
		JSONSymbol symbolSeven = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbolEight = new JSONSymbol(JSONSymbol.Type.NUMBER, "9872");
		JSONSymbol symbolNine = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbolTen = new JSONSymbol(JSONSymbol.Type.NUMBER, "9.872");
		JSONSymbol symbolEleven = new JSONSymbol(JSONSymbol.Type.COMMA);
		JSONSymbol symbolTwelve = new JSONSymbol(JSONSymbol.Type.STRING, "This should be a String");
		JSONSymbol symbolThirteen = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
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
		symbolsList.add(symbolTen);
		symbolsList.add(symbolEleven);
		symbolsList.add(symbolTwelve);
		symbolsList.add(symbolThirteen);
		
		JSONArray firstArray = Parser.parseArray(symbolsList);
		assertEquals(6, firstArray.thisArrayContents.size());
		assertEquals(null, firstArray.thisArrayContents.get(0));
		assertEquals(true, firstArray.thisArrayContents.get(1));
		assertEquals(false, firstArray.thisArrayContents.get(2));
		assertEquals((long) 9872, firstArray.thisArrayContents.get(3));
		assertEquals(9.872, firstArray.thisArrayContents.get(4));
		assertEquals("This should be a String", firstArray.thisArrayContents.get(5));
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
		assertEquals(1, firstObject.thisObjectContents.size());
		assertEquals(null, firstObject.thisObjectContents.get(""));
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
		assertEquals(1, firstObject.thisObjectContents.size());
		assertEquals(true, firstObject.thisObjectContents.get("Hey look, a String"));
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
		assertEquals(1, firstObject.thisObjectContents.size());
		assertEquals("I wanna be a String too!", firstObject.thisObjectContents.get("Ohhhweee, A brand new String!"));
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
		assertEquals(1, firstObject.thisObjectContents.size());
		assertEquals((long) 3574, firstObject.thisObjectContents.get("A String? Well would you look at that!"));
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
		assertEquals(1, firstObject.thisObjectContents.size());
		assertEquals((double) 3.574, firstObject.thisObjectContents.get("Another String? Well would you look at that!"));
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
		assertEquals(1, firstObject.thisObjectContents.size());
		JSONArray secondArray = (JSONArray) firstObject.thisObjectContents.get("One String coming up!");
		assertEquals(0, secondArray.thisArrayContents.size());
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
		assertEquals(1, firstObject.thisObjectContents.size());
		JSONArray secondArray = (JSONArray) firstObject.thisObjectContents.get("Another String coming up!");
		assertEquals(1, secondArray.thisArrayContents.size());
		assertEquals((double) 734.727, secondArray.thisArrayContents.get(0));
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
		assertEquals(1, firstObject.thisObjectContents.size());
		JSONObject secondObject = (JSONObject) firstObject.thisObjectContents.get("Time to try this String");
		assertEquals(0, secondObject.thisObjectContents.size());
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
		assertEquals(1, firstObject.thisObjectContents.size());
		JSONObject secondObject = (JSONObject) firstObject.thisObjectContents.get("Uno, Duno, String");
		assertEquals(1, secondObject.thisObjectContents.size());
		assertEquals(false, secondObject.thisObjectContents.get("Tres, Quatre, String"));
	}
	
	@Test
	public void parsingIncorrectEmptyObjectTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.RIGHT_SQUARE_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		
		boolean exception = false;
		String customMessage = "";
		
		try {
			Parser.parseObject(symbolsList);
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		
		assertTrue(exception);
		assertEquals("Error! This is an invalid Object.", customMessage);
	}

	@Test
	public void parsingIncorrectObjectWithoutCommaTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.NUMBER, "7656");
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolFour = new JSONSymbol(JSONSymbol.Type.NUMBER, "76.56");
		JSONSymbol symbolFive = new JSONSymbol(JSONSymbol.Type.STRING, "String To me, String to you");
		JSONSymbol symbolSix = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolSeven = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		JSONSymbol symbolEight = new JSONSymbol(JSONSymbol.Type.RIGHT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		symbolsList.add(symbolFour);
		symbolsList.add(symbolFive);
		symbolsList.add(symbolSix);
		symbolsList.add(symbolSeven);
		symbolsList.add(symbolEight);
		
		boolean exception = false;
		String customMessage = "";
		try {
			Parser.parseObject(symbolsList);
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! Missing comma value in Object.", customMessage);
	}
	
	@Test
	public void parsingIncorrectObjectTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.LEFT_CURLY_BRACKET);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		boolean exception = false;
		String customMessage = "";
		try {
			Parser.parseObject(symbolsList);
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid Object.", customMessage);
	}

	@Test
	public void parsingCorrectTrueBooleanHash() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.STRING, "I'm a KEY");
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		
		ArrayList<Object> hashPair = Parser.parseHash(symbolsList);
		assertEquals(2, hashPair.size());
		assertEquals("I'm a KEY", hashPair.get(0));
		assertEquals(true, hashPair.get(1));
	}
	
	@Test
	public void parsingCorrectNullHashTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.STRING, "I'm a Key");
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.NULL_BOOLEAN);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		
		ArrayList<Object> hashPair = Parser.parseHash(symbolsList);
		assertEquals(2, hashPair.size());
		assertEquals("I'm a Key", hashPair.get(0));
		assertEquals(null, hashPair.get(1));
	}
	
	@Test
	public void parsingCorrectStringHashTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.STRING, "I'm a Key");
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.STRING, "Look at that, a correct String");
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		
		ArrayList<Object> hashPair = Parser.parseHash(symbolsList);
		assertEquals(2, hashPair.size());
		assertEquals("I'm a Key", hashPair.get(0));
		assertEquals("Look at that, a correct String", hashPair.get(1));
	}
	
	@Test
	public void parsingCorrectLongHashTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.STRING, "I'm a Key");
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.NUMBER, "62864");
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		ArrayList<Object> hashPair = Parser.parseHash(symbolsList);
		assertEquals(2, hashPair.size());
		assertEquals("I'm a Key", hashPair.get(0));
		assertEquals((long) 62864, hashPair.get(1));
	}
	
	@Test
	public void parsingCorrectDoubleHashTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.STRING, "I'm a Key");
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.NUMBER, "6.2864");
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		ArrayList<Object> hashPair = Parser.parseHash(symbolsList);
		assertEquals(2, hashPair.size());
		assertEquals("I'm a Key", hashPair.get(0));
		assertEquals((double) 6.2864, hashPair.get(1));
	}
	
	@Test
	public void parsingIncorrectHashTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.STRING, "I'm a Key");
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		boolean exception = false;
		String customMessage = "";
		try {
			Parser.parseHash(symbolsList);
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid Hash.",customMessage);
	}
	
	@Test
	public void parsingIncorrectHashWithoutColonTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.STRING, "I'm a Key");
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		boolean exception = false;
		String customMessage = "";
		try {
			Parser.parseHash(symbolsList);
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid Hash.",customMessage);
	}
	
	@Test
	public void parsingIncorrectHashWithValueTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.STRING, "I'm a Key");
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.SPACE);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		boolean exception = false;
		String customMessage = "";
		try {
			Parser.parseHash(symbolsList);
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! A Hash cannot contain this value.",customMessage);
	}
	
	@Test
	public void parsingIncorrectHashWithoutStringThrowsExceptionTest() throws CustomException {
		JSONSymbol symbolOne = new JSONSymbol(JSONSymbol.Type.NULL_BOOLEAN);
		JSONSymbol symbolTwo = new JSONSymbol(JSONSymbol.Type.COLON);
		JSONSymbol symbolThree = new JSONSymbol(JSONSymbol.Type.TRUE_BOOLEAN);
		ArrayList<JSONSymbol> symbolsList = new ArrayList<JSONSymbol>();
		symbolsList.add(symbolOne);
		symbolsList.add(symbolTwo);
		symbolsList.add(symbolThree);
		boolean exception = false;
		String customMessage = "";
		try {
			Parser.parseHash(symbolsList);
		} catch (CustomException error) {
			exception = true;
			customMessage = error.getMessage();
		}
		assertTrue(exception);
		assertEquals("Error! This is an invalid Hash.",customMessage);
	}
}
	