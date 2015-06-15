package matrixdistance;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Sheet 8, Ex 1
 *
 * @author Antonio Rajic, Daniel O'Grady
 *
 */
public class MatrixDistance {

	/**
	 * Creates the difference-matrix between two arrays x and y
	 *
	 * @param x
	 *            first array
	 * @param y
	 *            second array
	 * @return difference matrix, where every field is d(x,y)=|x-y|
	 */
	public static int[][] getMatrix(final int[] x, final int[] y) {
		final int[][] matrix = new int[x.length][y.length];
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < y.length; j++) {
				matrix[i][j] = Math.abs(x[i] - y[j]);
			}
		}
		return matrix;
	}

	/**
	 * Finds the shortest path through the matrix, that is, from the top left
	 * corner (0|0) to the bottom right corner (n|m)
	 *
	 * @param matrix
	 *            matrix to find the path through
	 * @return list of pairs of coordinates to get from (0|0) to (n|m)
	 */
	public static List<List<Integer>> getShortestPath(final int[][] matrix) {
		final List<List<Integer>> steps = new LinkedList<List<Integer>>();
		int i = 0;
		int j = 0;
		while (i < matrix.length && j < matrix[i].length) {
			steps.add(Arrays.asList(i, j));
			int right, down, downright, min;
			right = valueAt(matrix, i + 1, j);
			down = valueAt(matrix, i, j + 1);
			downright = valueAt(matrix, i + 1, j + 1);
			min = min(right, down, downright);
			// prefer diagonal movement
			if (downright == min) {
				i++;
				j++;
			} else if (down == min) {
				j++;
			} else {
				i++;
			}

		}
		return steps;
	}

	/**
	 * @param matrix
	 *            matrix to get the value from
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @return value ata (x|y) if the coordinate are valid,
	 *         {@link Integer#MAX_VALUE} if not
	 */
	private static int valueAt(final int[][] matrix, final int x, final int y) {
		return x < matrix.length && y < matrix[x].length ? matrix[x][y] : Integer.MAX_VALUE;
	}

	/**
	 * @param values
	 *            values to determine the minimum from. Replaces Math.Min(a,
	 *            Math.Min(b, Math.Min(c,...))))))))
	 * @return smallest of the passed values
	 */
	private static int min(final int... values) {
		int min = values[0];
		for (int i = 1; i < values.length; i++) {
			if (values[i] < min) {
				min = values[i];
			}
		}
		return min;
	}

	/**
	 * Pretty-print for a 2d-int-matrix
	 *
	 * @param matrix
	 *            matrix to print
	 */
	private static void printMatrix(final int[][] matrix) {
		for (final int[] element : matrix) {
			for (final int element2 : element) {
				System.out.print(String.format("%3d ", element2));
			}
			System.out.print("\r\n");
		}
	}

	public static void main(final String[] args) {
		final int[] x = { 0, 0, 1, 4, 7, 14, 26, 23, 8, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0 };
		final int[] y = { 0, 0, 0, 0, 0, 0, 1, 5, 6, 13, 25, 24, 9, 4, 2, 1, 0, 0, 0, 0 };

		// x = new int[] { 1, 2, 2, 3, 4, 4, 1, 0 };
		// y = new int[] { 0, 1, 2, 2, 3, 4, 3, 2, 1 };
		final int[][] matrix = getMatrix(x, y);
		final List<List<Integer>> path = getShortestPath(matrix);
		printMatrix(matrix);
		System.out.println(path);
	}
}
