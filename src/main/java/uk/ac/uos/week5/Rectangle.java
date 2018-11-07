package uk.ac.uos.week5;

public class Rectangle extends Shapes 
{  // fields 
   double length, width; 
   // constructors
   public Rectangle()
   {
      name = "Rectangle";
      length = width = 0;
   }
   public Rectangle(double _length, double _width) 
   { 
      name = "Rectangle"; 
      length = _length; 
      width = _width; 
   } 
   // methods 
   public void computeArea() 
   { 
      area = length * width; 
   } 
   public void computePerimeter() 
   { 
      perimeter = 2*(length + width); 
   } 
}
