import java.util.Scanner;

public class GuessingGame {

	public GuessingGame(){
		Boolean running = true;
		Boolean playing = false;
		Boolean startScreen = true;
		Boolean stopScreen = false;

		int guesses;

		double random;
		int target;

		String input;
		int guess;

		Scanner scanner = new Scanner(System.in);

		while(running == true) {
			random = Math.random();
			target = (int)(random * 100);
			guesses = 10;
			while(startScreen == true) {
				// System.out.println(target);
				System.out.println("Type Start To Begin...");
				input = scanner.nextLine();
				input = input.toLowerCase();
				if(input.equals("start")){
					playing = true;
					startScreen = false;
				}
			}
			System.out.println("Please type a number between 0 and 100!");
			while(playing == true) {
				input = scanner.nextLine();
				if(input.matches("[0-9]+")) {
					
					guess = Integer.parseInt(input);
					if(guess == target){
						guesses--; // we do this so that the number of guesses left is accurate for the player to see
						System.out.println("Correct! The number was " + target + "! You won with " + guesses + " remaining guesses!");
						playing = false;
						stopScreen = true;
					} else if(guess > 100 || guess < 0) {
						System.out.println("Please pick a guess between 0 and 100!");
					} else if(guess != target) {
						guesses--;
						System.out.println("Incorrect. Try again! " + guesses + " remaining guesses.");
					}
					if(guesses <= 0) {
						System.out.println("Incorrect. You lost =( The number was " + target + "!");
						playing = false;
						stopScreen = true;
					}

				} else {
					System.out.println("Please type a number between 0 and 100!");
				}
			}

			while(stopScreen == true) {
				System.out.println("Do you want to play again? Type Yes or No.");
				input = scanner.nextLine();
				if (input.toLowerCase().equals("yes")) {
					stopScreen = false;
					startScreen = true;
				} else if (input.toLowerCase().equals("no")) {
					scanner.close();
					running = false;
					return;
				}
			}

		}
	}
	public static void main(String[] args) {
		new GuessingGame();
	}
}