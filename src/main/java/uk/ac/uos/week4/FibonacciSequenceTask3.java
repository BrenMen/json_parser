package uk.ac.uos.week4;

import java.util.ArrayList;

public class FibonacciSequenceTask3 {

	    public static void main(String[] args) {

	    	ArrayList<Integer> arrayList=new ArrayList<Integer>();
	    	
	        int n = 20, t1 = 0, t2 = 1;
	       
	        for (int i = 1; i <= n; ++i)
	        {
	            arrayList.add(t1);

	            int sum = t1 + t2;
	            t1 = t2;
	            t2 = sum;
	        }
	        System.out.print("First " + n + " terms: " + arrayList);
	    }
	}