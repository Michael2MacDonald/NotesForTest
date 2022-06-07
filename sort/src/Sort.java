/**
 * By Micahel MacDonald
 * 1/8/2022
 * reads a list of numbers from a txt file in csv format and allows you to sort them using 4 sorting methods
 * (Bubble, Selection, Table, Quick)
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Sort {

	Scanner consoleInput = new Scanner(System.in);
	Scanner fileInput;
	String input;
	int[] inputArray;

	long startTime;
	long endTime;

	public Sort() {
		System.out.println("Select a file:");
		System.out.println("1: input1.txt");
		System.out.println("2: input2.txt");
		System.out.println("3: input3.txt");
		System.out.println("4: input4.txt");
		input = consoleInput.nextLine();

		if ( input.length()!=1 && input.charAt(0)!=1 && input.charAt(0)!=2 && input.charAt(0)!=3 && input.charAt(0)!=4 ) {
			System.out.println("Enter 1, 2, 3, or 4");
			while ( input.length()!=1 && input.charAt(0)!=1 && input.charAt(0)!=2 && input.charAt(0)!=3 && input.charAt(0)!=4 ) {
				input = consoleInput.nextLine();
			}
		}

		try {
			fileInput = new Scanner(new File("input"+input.charAt(0)+".txt"));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}

		String infile = fileInput.nextLine();
		String[] inputStringArray = infile.split(",");

		inputArray = new int[inputStringArray.length];

		for (int i = 0; i < inputStringArray.length; i++) {
			inputArray[i] = Integer.parseInt(inputStringArray[i]);
			// System.out.println(inputArray[i]); // print the unsorted list
		}

		System.out.println("Select a sorting algorithm:");
		System.out.println("1: Bubble");
		System.out.println("2: Selection");
		System.out.println("3: Table");
		System.out.println("4: Quicksort");

		input = consoleInput.nextLine();

		if ( input.length()!=1 && input.charAt(0)!=1 && input.charAt(0)!=2 && input.charAt(0)!=3 && input.charAt(0)!=4 ) {
			System.out.println("Enter 1, 2, 3, or 4");
			while ( input.length()!=1 && input.charAt(0)!=1 && input.charAt(0)!=2 && input.charAt(0)!=3 && input.charAt(0)!=4 ) {
				input = consoleInput.nextLine();
			}
		}

		startTime = System.currentTimeMillis();
		switch (Integer.parseInt(input)) {
			case 1:
				inputArray = bubbleSort(inputArray);
				break;
			case 2:
				inputArray = selectionSort(inputArray);
				break;
			case 3:
				inputArray = tableSort(inputArray);
				break;
			case 4:
				System.out.println("Starting QuickSort"); // Can't put in function due to recursion
				inputArray = quickSort(inputArray,0,inputArray.length-1);
				break;
		}
		endTime = System.currentTimeMillis();

		System.out.print("It Took ");System.out.print(endTime-startTime);System.out.println(" Millisecond(s) To Run");

		// Print the sorted list
		// for (int i = 0; i < inputArray.length; i++) {
		// 	System.out.println(inputArray[i]);
		// }

		// output to file
		File selectedFile = new File("output.txt");
		try (FileWriter out = new FileWriter(selectedFile)) {
			for (int i = 0; i < inputArray.length; i++) {
				out.write(String.valueOf(inputArray[i]));
				out.write(",");
			}
			out.close();
		} catch (IOException ex) {
			// lol just forget than anything bad happened
			ex.printStackTrace();
		}

	}

	public int[] bubbleSort(int[] array) {
		System.out.println("Starting Bubble Sort");

		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length - 1; i++) {
				if (array[i]>array[i+1]) {
					int temp = array[i];
					array[i] = array[i+1];
					array[i+1] = temp;
				}
			}
		}

		return array;
	}

	public int[] selectionSort(int[] array) {
		System.out.println("Starting Selection Sort");


		for (int j = 0; j < array.length; j++) {
			int smallestNumber = array[j];
			int smallestIndex = j;
			for (int i = j; i < array.length; i++) {
				if (array[i] < smallestNumber) {
					smallestNumber = array[i];
					smallestIndex = i;
				}
			}
			int temp = array[smallestIndex];
			array[smallestIndex] = array[j];
			array[j] = temp;
		}

		return array;
	}

	public int[] tableSort(int[] array) {
		System.out.println("Starting Table Sort");

		int[] tally = new int[1001];
		for (int i = 0; i < array.length; i++) {
			tally[array[i]]++;
		}

		int count = 0;
		// keep track of number
		for (int i = 0; i < tally.length; i++) {
			// keep track of number of times we see that number
			for (int j = 0; j < tally[i]; j++) {
				array[count] = i;
				count++;
			}
		}

		return array;
	}

	public int quickSortPart(int[] array, int left, int right) {
		int temp;
		int pivot = right;
		int leftIndex = left;
		int rightIndex = right - 1;

		while(true) {
			while (array[leftIndex] < array[pivot]) { leftIndex++; } // Search for the next value larger than the pivot (midpoint) starting from the left
			
			while(rightIndex > 0 && array[rightIndex] > array[pivot]) { rightIndex--; } // Search for the next value smaller than the pivot (midpoint) starting from the right
			
			if(leftIndex >= rightIndex) { // If your searches from the left and right collide with eachother then we have finished searching
				break;
			} else { // Else, we have found a value on the left that is larger than the pivot and we have found a value on the right that is smaller than the pivot so switch them.
				temp = array[leftIndex];
				array[leftIndex] = array[rightIndex];
				array[rightIndex] = temp;
			}
		}

		// Once the sorting is done we can move the pivot so that smaller numbers are on its left and larger numbers are on its right
		temp = array[leftIndex];
		array[leftIndex] = array[pivot];
		array[pivot] = temp;

		// Return the leftIndex so that we can recursively sort the two sections on both sides of the pivot just like we did here
		return leftIndex;
	}

	// Recursively sorts the array into two sections divided by the 'pivot'.
	// It gets the pivot value from the end of the array,
	// sorts numbers larger and smaller that the pivot,
	// and then moves the pivot to the spot between the the two sections it sorted.
	// It is recursive because for each section it creates by sorting, it is called again to sort that section.
	public int[] quickSort(int[] array, int left, int right) {
		if(left < right) {
			// Make the 'pivot' the last number in the array between the indexes 'left' and 'right'
			// Sort all numbers smaller than the pivot to the left of the pivot
			// Sort all numbers larger than the pivot to the right of the pivot
			int partition = quickSortPart(array, left, right);
			// Call this function recursively and sort the two new sections formed
			// to the left and right of the pivot from the parent recursive function
			// call (or origanal function call)
			quickSort(array, left, partition-1); // left index->
			quickSort(array, partition+1, right);
		}

		/** DEV NOTE:
		 * This function would not work in C/C++!
		 * We are not saving the returned array value from each consecutive recursive function call.
		 * In C/C++ this would mean that the sorting from any recursive function calls would be lost.
		 * BUT in Java this code does work and I had to waste time figuring out why it was working!
		 * It turns out that in Java the array is an object and all objects are references/pointers.
		 * This means that when we pass a variable to a function we are not passing it the value of
		 * the variable like in C/C++, we are really passing it a pointer to the origanal variable.
		 */

		return array;
	}

	public static void main(String[] args) {
		new Sort();
	}
}
