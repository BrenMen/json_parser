package uk.ac.uos.assignment;

import java.util.Map;
import java.util.HashMap;

public class JSONObject {
	Map<String, Object> thisObjectContents;

	// Using HashMap so that a JSONObject can be parsed into the constructor.
	JSONObject(HashMap<String, Object> map) {
		this.thisObjectContents = map;
	}
}
