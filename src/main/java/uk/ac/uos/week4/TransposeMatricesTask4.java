package uk.ac.uos.week4;

public class TransposeMatricesTask4 {
	public static void main(String args[]) {
		int original[][] = { { 1, 3, 4 }, { 2, 4, 3 }, { 3, 4, 5 } };
		int transpose[][] = new int[3][3];
		// transpose the matrix
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				transpose[i][j] = original[j][i];
			}
		}
		// print out the original matrix
		System.out.println("Original Matrix:");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(original[i][j] + " ");
			}
			System.out.println();
		}
		// print out the transposed matrix
		System.out.println("Transposed Matrix:");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(transpose[i][j] + " ");
			}
			System.out.println();
		}
	}
}