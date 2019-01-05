package uk.ac.uos.assignment;

import java.util.List;
import java.util.ArrayList;

public class JSONArray {
	List<Object> thisArrayContents = new ArrayList<Object>();

	// Using arrayList so that a JSONArray can be parsed into the constructor.
	JSONArray(ArrayList<Object> array) {
		this.thisArrayContents = array;
	}
}