package uk.ac.uos.assignment;

import java.util.*;  

public class Description implements Describe {
	private Collection<Describe> items;
	
	public Description() {
		this.items = new ArrayList<>();
	}
	
	public String describe() {
		return Collections.toString(items);
	}

	public void add(Describe d) {
		items.add(d);		
	}
}