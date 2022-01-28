// Practical midterm notes. Feel free to share with your friends (before the test) 
// Not sure if you're allowed to share notes with people who haven't taken the test after you've already taken the test tho, so ask the teacher first if you're gonna do that 
// If you do something stupid with these notes it's your fault, not mine lol 

// Everything is in each method, there are no globals 

// If you are using Eclipse you can press Ctrl Shift O to auto import stuff 

import java.util.*; 
import java.io.*;

public class PracticalMidtermNotes {

	public static void main(String[] args) {
		// Test stuff here 
		// To test some of the functions below you can simply call them for here 
		// For example, to call the method askForString, you would type in "askForString()" (without the quotes) below. 
		// Hopefully you know how to call methods already, because if don't you're probably kinda screwed for this test lol 
		// You can also test other code in here but I think you can just do that from your eclipse project. 
		// Also, "methods" are commonly referred to as "functions" by a lot of people. 
		sampleProblem(); // Run the sample problem thingy 
	}

	// Ask the user for a string input and print out how many times each letter appears. This is the sample problem on the Canvas module thingy 
	public static void sampleProblem() {
		Scanner input = new Scanner(System.in); // Create the thing that takes input from stuff you type in a console 
		String in = input.nextLine(); // Get the input
		int[] counts = new int[255]; // Create an array of 255 ints. This is excessive but it works. We wll be using chars to access this array. 
		for (int i = 0; i < in.length(); i++) { // Loop through the string 
			char c = in.charAt(i); // Get the char at the current index 
			if (c >= 'a' && c <= 'z') { // Check if the char is a lowercase letter
				counts[c]++; // Increment the count of the letter 
			}
			else if (c >= 'A' && c <= 'Z') { // Check if the char is an uppercase letter
				counts[c + 32]++; // Convert the letter to lowercase and increment the count of the letter 
			}
		}
		for (char i = 0; i < counts.length; i++) { // Loop through the counts array. Make sure to use a char so you don't have to cast it later
			if (counts[i] > 0) { // If the count is greater than 0 
				System.out.println(i + ":" + counts[i]); // Print out the char and the count 
			}
		}
		input.close(); // Close the scanner
	}

	// Read a file and print out the contents of the file. 
	public static void readFile() {
		try { // Using try catch so the program doesn't die if there's an error. You probably don't need this but I like to do it 
			Scanner fileInput = new Scanner(new File("FILENAME.txt")); // Create a scanner that reads from the file named FILENAME.txt (you can also put a full path in here)
			while (fileInput.hasNextLine()) { // While there is still more to read 
				String line = fileInput.nextLine(); // Get the next line 
				System.out.println(line); // Print out the line 
			}
			fileInput.close(); // Close the file 
		} 
		catch (FileNotFoundException e) { // If the file wasn't found 
			System.out.println("The file was not found!"); // Notify the user that the program couldn't find the file 
		} 
	}

	// Write a file. Ask the user for a file name and a string to write to the file.
	public static void writeToFile() {
		Scanner input = new Scanner(System.in); // Create the thing that takes input from stuff you type in a console 
		System.out.println("Enter a file name: "); // Ask the user for a file name 
		String fileName = input.nextLine(); // Get the file name 
		System.out.println("Enter a string to write to the file: "); // Ask the user for a string to write to the file 
		String in = input.nextLine(); // Get the string 
		try { // Using try catch so the program doesn't die if there's an error. You probably don't need this but I like to do it 
			PrintWriter fileOutput = new PrintWriter(fileName); // Create a PrintWriter that writes to the file named FILENAME.txt (you can also put a full path in here)
			fileOutput.println(in); // Write the string to the file 
			fileOutput.close(); // Close the file 
		} 
		catch (FileNotFoundException e) { // If the file wasn't found 
			System.out.println("The file was not found!"); // Notify the user that the program couldn't find the file 
		} 
		input.close(); // Close the scanner
	}

	// Ask the user for an int and print it out
	public static void askForInt() {
		Scanner input = new Scanner(System.in); // Create the thing that takes input from stuff you type in a console 
		System.out.println("Enter an int: "); // Ask the user for an int 
		try { // Using try catch so the program doesn't die if there's an error. You probably don't need this but I like to do it
			int in = input.nextInt(); // Get the int 
			System.out.println(in); // Print out the int 
		}
		catch (InputMismatchException e) { // If the user didn't enter an int 
			System.out.println("Bruh you didn't enter an int :("); // Notify the user that they didn't enter an int 
		}
		input.close(); // Close the scanner
	}
	// You can also use nextFloat(), nextDouble(), nextBoolean(), nextByte(), nextShort(), nextLong() and nextChar() to get the next input from the user. 

	// Ask the user for a string and print it out
	public static void askForString() {
		Scanner input = new Scanner(System.in); // Create the thing that takes input from stuff you type in a console 
		System.out.println("Enter a string: "); // Ask the user for a string 
		String in = input.nextLine(); // Get the string 
		System.out.println(in); // Print out the string 
		input.close(); // Close the scanner
	}

	// Ask the user for a char array and print it out
	public static void askForCharArray() {
		Scanner input = new Scanner(System.in); // Create the thing that takes input from stuff you type in a console 
		System.out.println("Enter a char array: "); // Ask the user for a char array 
		char[] in = input.nextLine().toCharArray(); // Get the char array 
		for (int i = 0; i < in.length; i++) { // Loop through the char array 
			System.out.println(in[i]); // Print out the char at the current index 
		}
		input.close(); // Close the scanner
	}

    // Ask the user for an int array, separated by spaces and print it out
    public static void askForIntArray() {
        Scanner input = new Scanner(System.in); // Create the thing that takes input from stuff you type in a console
        System.out.println("Enter an int array: "); // Ask the user for an int array
        String in = input.nextLine(); // Get the string
        String[] inArray = in.split(" "); // Split the string into an array of strings
        int[] intArray = new int[inArray.length]; // Create an int array of the same length as the string array
        for (int i = 0; i < inArray.length; i++) { // Loop through the string array
            intArray[i] = Integer.parseInt(inArray[i]); // Convert the string to an int and put it in the int array
        }
        for (int i = 0; i < intArray.length; i++) { // Loop through the int array
            System.out.println(intArray[i]); // Print out the int at the current index
        }
        input.close(); // Close the scanner
    }

    // Sort an array using the built-in sort method 
    public static void sortArray() {
        int[] intArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Create an array of ints
        Arrays.sort(intArray); // Sort the array using the built-in sort method
        for (int i = 0; i < intArray.length; i++) { // Loop through the array 
            System.out.println(intArray[i]); // Print out the int at the current index 
        }
    }

    // Really basic stuff, this is for if you forgot how to make an array or a scanner or something. 
    // If you're reading this before the test, STUDY THIS because it will be very painful to keep looking back at this during the test. 
    public static void reallyBasicStuff() {
        for (int i = 0; i < 10; i++) { // Basic for loop
            // A for loop is a loop that runs a block of code a certain number of times. 
            // The for loop looks like this: for (initialization; condition; increment) {
            // The initialization is the code that runs before the first iteration.
            // The condition is the code that runs after each iteration.
            // The increment is the code that runs after each iteration.
            // An iteration is a single run of the code inside the bracket thingy. 

            // In this example, the initialization is "int i = 0;"
            // This means that the variable i is set to 0. You will only be able to use i from inside the loop. When the loop starts, i is 0.
            // The condition is "i < 10;"
            // This means that the loop will run until i is less than 10. Basically, the code goes i = 0, i = 1, i = 2, ..., i = 9. 
            // The variable i does not reach 10, but since it started at 0, it will run 10 times. 
            // The increment is "i++;"
            // This means that the variable i is incremented by 1 after each iteration.
            // Basically we add 1 to i after every time the code in the loop finishes executing. 

            // So, in this case, the code will run 10 times.

            System.out.println(i); // Print out the the value of i
        }

        while (true) { // Basic while loop
            // A while loop is a loop that runs a block of code while a condition is true. 
            // The while loop looks like this: while (condition) {
            // The condition is the code that runs after each iteration.
            // An iteration is a single run of the code inside the bracket thingy. 

            // In this example, the condition is "true;"
            // This means that the loop will run forever until a break statement is reached. 
            // If the condition is "num == 10", the loop will run until num is not 10. 

            // So, in this case, the code would run forever since the condition is true. However, we have a "break;" statement in the code, which stops the loop. 

            System.out.println("Hello"); // Print out "Hello"
            break; // Break out of the loop (break statements also work in for loops) 
        }

        int intvar = 0; // This is an intenger variable. It's a variable that can only hold an integer. 
        float floatvar = 0.0f; // This is a float variable. It's a variable that can hold a floating point number. This can be a decimal. 
        double doublevar = 0.0; // This is a double variable. It's a variable that can hold a double. This can be a decimal.
        char charvar = 'a'; // This is a char variable. It's a variable that can hold a single character.
        boolean boolvar = true; // This is a boolean variable. It's a variable that can hold either true or false.
        String stringvar = "Hello"; // This is a string variable. It's a variable that can hold a string. A string is a sequence of characters. 

        // You can also convert between number types. 
        // For example, you can convert an int to a float.
        int epicTestInt = 5; // This is an int variable
        float epicTestFloat = (float)epicTestInt; // This is a float variable. The float variable is set to the value of the int variable.
        // The parentheses are important. They tell the compiler that you're converting from an int to a float. This conversion is called casting. 


        boolean[] boolArray = new boolean[10]; // Create a boolean array with a length of 10
        int[] intArray = new int[10]; // Create an int array with a length of 10
        char[] charArray = new char[10]; // Create a char array with a length of 10
        String[] stringArray = new String[10]; // Create a string array with a length of 10

        for (int i = 0; i < boolArray.length; i++) { // Loop through the boolean array 
            boolArray[i] = true; // Set the current index to true 
        }

        for (int i = 0; i < intArray.length; i++) { // Loop through the int array 
            intArray[i] = i; // Set the current index to i 
        }

        int[][] intArray2D = new int[10][10]; // Create a 2D int array with a length of 10 and a width of 10
        // You can think of this as a 10x10 grid of numbers. 

        for (int i = 0; i < intArray2D.length; i++) { // Loop through the 2D int array 
            for (int j = 0; j < intArray2D[i].length; j++) { // Loop through the 2D int array's current row 
                intArray2D[i][j] = i + j; // Set the current index to i + j 
            }
        }

        // The comparison operators are ==, !=, >, <, >=, <=
        // == checks if two things are equal (don't use this for strings. For strings, use .equals(otherString) instead)))
        // != checks if two things are not equal (don't use this for strings. For strings, check if .equals(otherString) is false instead)))
        // > checks if one thing is greater than another
        // < checks if one thing is less than another
        // >= checks if one thing is greater than or equal to another
        // <= checks if one thing is less than or equal to another

        // The logical operators are &&, ||, !
        // && is the AND operator. It checks if both things are true.
        // || is the OR operator. It checks if either thing is true.
        // ! is the NOT operator. It checks if the thing is false. You put this in front of a statement. 
        // For example, if str is a string that equals "hello", str.equals("hello") is true, so !str.equals("hello") is false. 

        // Let's say we wanted to check if the int variable "epicThingy" is either 3 or 5. 
        int veryEpicTestIntThingy = 3; // This is an int variable
        if (veryEpicTestIntThingy == 3 || veryEpicTestIntThingy == 5) { // Check if the int variable is either 3 or 5
            System.out.println("The int variable is either 3 or 5"); // Print out "The int variable is either 3 or 5"
        }
        else {
            System.out.println("The int variable is not 3 or 5"); // Print out "The int variable is not 3 or 5"
        }

        // The assignment operators are =, +=, -=, *=, /=, %=
        // The increment and decrement operators are ++, --
        // The ternary operator is ?: (you probably don't need to know this)



        int testInt = 0; // Create an int variable called testInt and set it to 0
        int otherTestInt = 0; // Create an int variable called otherTestInt and set it to 0
        if (testInt == otherTestInt) { // If testInt is equal to otherTestInt
            System.out.println("They are equal"); // Print out "They are equal"
        } 
        else { // If they are not equal 
            System.out.println("They are not equal"); // Print out "They are not equal"
        }

        String testString = "yes"; // Create a string called testString and set it to "yes"
        String otherTestString = "yes"; // Create a string called otherTestString and set it to "yes"
        if (testString.equals(otherTestString)) { // Check if testString is equal to otherTestString
            // For strings, you have to use the .equals() method. 
            // If you don't, it will just check if the two strings are the same object.
            // Basically it will return false a bunch of times when you don't want it to. 
            System.out.println("They are equal"); // If they are equal, print "They are equal"
        } 
        else { // If they are not equal 
            System.out.println("They are not equal"); // Print "They are not equal"
        }

        char[] charArrayThingy = {'a', 'b', 'c'}; // Create a char array called charArrayThingy and set it to {'a', 'b', 'c'}
        String testString2 = "yes"; // Create a string called testString2 and set it to the char array charArrayThingy
        char[] otherCharArrayThingy = testString2.toCharArray();
        String testString3 = new String(charArrayThingy); // Create a string called testString3 and set it to the char array charArrayThingy. The string will be "abc" 


        int[] intArrayThingy = {1, 2, 3}; // Create an int array called intArrayThingy and set it to {1, 2, 3} 

    }


}
