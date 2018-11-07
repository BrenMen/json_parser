package uk.ac.uos.week4;

public class ArrayTask1 {
	
	 public static void main(String[] args) {
		 int array[] = {17, 89, 27, 11, 91, 42, 73, 90, 5, 5};
		 int max = maximum(array);
		 int min = minimum(array);
		 int range = range(max, min);
		 int mean = mean(array);
		 
		 System.out.println("max: " + max);
		 System.out.println("min: " + min);
		 System.out.println("range: " + range);
		 System.out.println("mean: " + mean);
	 }
	 
	public static int maximum(int a[]) {
		int max = a[0];
		for(int i = 1; i < a.length;i++) {
			if(a[i] > max) {
				max = a[i];
			}
		}	
		return max;
	}
	
	public static int minimum(int a[]) {
		int min = a[0];
		for(int i = 1; i < a.length;i++) {
			if(a[i] < min)
			{
				min = a[i];
			}
		}	
		return min;
	}
	
	public static int range(int max, int min) {
		int range = max - min;
		return range;
	}

	public static int mean(int a[]) {
		int i = 0;
		int sum = 0;
		while (i < a.length) {
			sum += a[i];
			i++;
		}
		int mean = sum / a.length;
		return mean;
	}
}