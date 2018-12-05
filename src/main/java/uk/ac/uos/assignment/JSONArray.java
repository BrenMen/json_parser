package uk.ac.uos.assignment;

import java.util.ArrayList;
import java.util.List;

public class JSONArray {
	List<JSONObject> JSONArrayContent = new ArrayList<JSONObject>();

	// Using array list so that the JSONArray can be parsed into the constructor.
	JSONArray(ArrayList<JSONObject> array) {
		this.JSONArrayContent = array;
	}
}
