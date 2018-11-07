package uk.ac.uos.week6;

import java.util.*;  

class Call {
	public static void main(String[] args) {
		Describe collection[] = new Describe[4];
		collection[0] = new Invalid();
		collection[1] = new Number();
		collection[2] = new Text();
		collection[3] = new Truth();

		ArrayList<String> list = new ArrayList<String>();

		for (int x = 0; x < 4; ++x) {
			list.add(collection[x].describe());
		}
		System.out.println(list);
	}
}

//yee