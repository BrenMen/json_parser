package uk.ac.uos.test;

import java.util.Scanner;
import java.text.DecimalFormat;

public class Perimeter {
	private static DecimalFormat df2 = new DecimalFormat(".##");
	
	public static void main( String[] args )
	{
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a radius: ");
		int radius = reader.nextInt();
		reader.close();
		double pie = Math.PI;
		circle(radius, pie);
		sphere(radius, pie);
	}
	
	public static void circle(int radius, double pie) {
		double areaForCircle = pie * radius * radius;
		double perimeterForCircle = 2 * pie * radius;
		System.out.println("area for circle: " + df2.format(areaForCircle));
		System.out.println("perimeter for circle: " + df2.format(perimeterForCircle));
	}
	
	public static void sphere(int radius, double pie) {
		double surfaceAreaForSphere = 4 * pie * radius * radius;
		double volumeForSphere = 4 * pie * radius * radius * radius / 3;
		System.out.println("surface area for sphere: " + df2.format(surfaceAreaForSphere));
		System.out.println("volume for sphere: " + df2.format(volumeForSphere));
	}
}