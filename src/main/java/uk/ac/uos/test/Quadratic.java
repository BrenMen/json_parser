package uk.ac.uos.test;

import java.util.Scanner;
import java.text.DecimalFormat;

public class Quadratic
{
	private static DecimalFormat df2 = new DecimalFormat(".##");
	
	public static void main( String[] args )
	{
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter value a: ");
		int a = reader.nextInt();
		System.out.println("Enter value b: ");
		int b = reader.nextInt();
		System.out.println("Enter value c: ");
		int c = reader.nextInt();
		reader.close();
		double quadraticFormula = (-b + (Math.sqrt((b * b) - 4 * a * c))) / (2 * a);
		//double solveFormula = a * x * x + b * x + c;
		System.out.println(df2.format(quadraticFormula));
	}
}