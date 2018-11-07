package uk.ac.uos.test;

import java.util.Scanner;

public class UserSum
{
	public static void main( String[] args )
	{
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a number: ");
		int first = reader.nextInt();
		int second = reader.nextInt();
		reader.close();
		int sum = first + second;
		int difference = first - second;
		int divide = first / second;
		int product = first * second;
		System.out.println("sum: " + sum);
		System.out.println("difference: " + difference);
		System.out.println("product: " + product);
		System.out.println("division: " + divide);
	}
}