package uk.ac.uos.week4;

import java.util.*;

public class DiceTask2 {
	
	 public static void main(String[] args) {
		 int dice1[] = {1, 2, 3, 4, 5, 6};
		 int dice2[] = {1, 2, 3, 4, 5, 6};
		 ArrayList<Integer> arrayList = DiceCalculator(dice1, dice2);
		 System.out.println("All dice combinations: " + arrayList);
	 }
	 
	public static ArrayList<Integer> DiceCalculator(int dice1[], int dice2[]) {
		ArrayList<Integer> arrayList=new ArrayList<Integer>();
		for(int x = 0; x < dice2.length; x++) {
			for(int i = 0; i < dice1.length; i++) {
				if(x < dice2.length) {
					arrayList.add(dice1[i] + dice2[x]);
				}
			}
		}
		return arrayList;
	}
}