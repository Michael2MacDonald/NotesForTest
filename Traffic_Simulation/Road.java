import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Road extends JPanel {
	final int LANE_HEIGHT = 120;
	final int ROAD_WIDTH = 800;
	ArrayList<Vehicle> cars = new ArrayList<Vehicle>(); // Holds all of the cars that are on the road
	int carCount = 0; // used to calc through put

	Random random = new Random(); // for random number generation
	
	public Road() {
		super();
	}
	
	public void addCar(Vehicle v) { // add a car to the road
		cars.add(v);
	}
	
	public void paintComponent(Graphics g) { // Draw the road and cars
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0,  0,  this.getWidth(), this.getHeight()); // Draw the black road
		g.setColor(Color.WHITE);
		for (int a = LANE_HEIGHT; a < LANE_HEIGHT * 4; a = a + LANE_HEIGHT) { // Loop through each lane
			for (int b = 0; b < getWidth(); b = b + 40) { // Draw the lane dividers (30px wide with 10px gap) until the end of the road
				g.fillRect(b, a, 30, 5);
			}
		}
		// Draw the cars
		for (int a = 0; a < cars.size(); a++) { // For each car
			cars.get(a).paintMe(g); // Paint the car
		}
		
		
	}
	
	public void step() {
		for (int a = 0; a < cars.size(); a++) { // for each car
			Vehicle v = cars.get(a);

			// If there is no collision 
			if (collision(v.getX()+v.getSpeed(), v.getY(), v) == false) {
				// prevent gridlock; for example 4 semis, each in a different lane but lined up, will forever prevent anything from passing
				int step_amount = v.getSpeed() + (random.nextInt(6) - 3); // add variability to speed; v.getSpeed + ({1,2,3,4,5}-3) = v.getSpeed + {-2,-1,0,1,2}
				v.setX(v.getX() + step_amount); // Move the car by the speed
				if (v.getX() > ROAD_WIDTH) { // if the car goes off the end of the road
					if (collision(0, v.getY(), v) == false) { // if there is not a car at the start of the lane
						v.setX(0); // go to start of lane
						carCount++; // increment the car count for through put
					}
				}
			} else { // If there is going to be a collision

				if ((v.getY() > 40) && (collision(v.getX(), v.getY() - LANE_HEIGHT, v) == false)) { // move the car up a lane
					v.setY(v.getY() - LANE_HEIGHT);
				}
				else if ((v.getY() < (3 * LANE_HEIGHT)) && (collision(v.getX(), v.getY() + LANE_HEIGHT, v) == false)) { // move the car down a lane
					v.setY(v.getY() + LANE_HEIGHT);
				}
			}

		}
	}
	
	public boolean collision(int x, int y, Vehicle v) {
		for (int a = 0; a < cars.size(); a++) { // For each other car
			Vehicle u = cars.get(a);
			if (u.equals(v) != true) { // Don't check the car against itself
				if (y == u.getY()) { // If both cars are in the same lane
					// This car's back end is behind the front end of the other car AND this car's front end is in front of the other car's back end
					if ( (x < u.getX() + u.getWidth()) && (x + v.getWidth() > u.getX()) ) {
						return true;
					}
				}
			}
		}
		return false;
	}


	public int getCarCount() {
		return carCount;
	}
	
	public void resetCarCount() {
		carCount = 0;
	}
	
}
