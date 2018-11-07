package uk.ac.uos.week5;

public class Shapes { // fields
	String name;
	double area, perimeter;

	// constructor
	public Shapes() {
		name = "undetermined";
		area = perimeter = 0;
	}

	// methods
	public void display() {
		System.out.println("Name: " + name);
		System.out.println("Area: " + area);
		System.out.println("Perimeter: " + perimeter);
	}

	public static void main(String args[]) {
		Shapes s = new Shapes();
		Rectangle r = new Rectangle(2.0, 3.0);
		Circle c = new Circle(4.0);

		r.computeArea();
		r.computePerimeter();
		c.computeArea();
		c.computePerimeter();
		r.display();
		c.display();
		s.display();
	}
}
