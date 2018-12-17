package uk.ac.uos.assignment;

import java.util.HashMap;
import java.util.Map;

public class JSONObject {
	Map<String, Object> JSONObjectContents;

	// Using array list so that the JSONobject can be parsed into the constructor.
	JSONObject(HashMap<String, Object> map) {
		this.JSONObjectContents = map;
	}
}
