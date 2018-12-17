package uk.ac.uos.assignment;

import java.util.ArrayList;
import java.util.List;

public class JSONArray {
	List<Object> JSONArrayContents = new ArrayList<Object>();

	// Using array list so that the JSONArray can be parsed into the constructor.
	JSONArray(ArrayList<Object> array) {
		this.JSONArrayContents = array;
	}
}
