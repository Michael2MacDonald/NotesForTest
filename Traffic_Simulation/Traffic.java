/** Traffic Simulation Program - Michael MacDonald - 05/03/2022
 * Overview:
 * 	This program simulates traffic on a four-lane highway.
 * 
 * You can start and stop the simulation and you can add different vehicles to the simulation.
 * 
 * There are three types of cars: 
 * 	semis, SUVs, and sports cars.
 * 	- Semis have the largest footprint, accelerate the slowest, and have the lowest top speed.
 * 	- SUVs will be moderate for all three properties.
 * 	- Sports cars are the smallest, accelerate the fastest, and have the highest top speed.
 * 	There are 3 buttons to add any car to the simulation.
 * 
 * How it works:
 * 	If a car will collide with the car ahead of it, the car should check to see if it can switch
 * 	lanes to its immediate left or right, and otherwise will slow down.
 * 	You can view the car through put in the bottom right corner.
 * 
 * 	For each 'step' of the simulation, the cars will move forward by their speed +/- a random number between -2 and 2.
 * 
 * 
 * - I made Vehicle.paintMe() abstract because it makes more sense that way
 * - Fixed bug where cars would be able to switch lanes one lower than the lowest lane, creating effectively 5 lanes
 * - Made vehicle width, height, speed constants
 * - fixed throughput calculation (divide by 1000 NOT multiply by 1000!!!!), also limited the displayed digits to 5 (1 digit + 4 decimal places)
 * - randomized the lane that cars start in when adding a car
 * - other stuff
 * - comments
 */




import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Traffic implements ActionListener, Runnable {

	Random random = new Random(); // for random number generation

	JFrame frame = new JFrame("Traffic Simulation");
	Road road = new Road();
	//South container
	JButton start = new JButton("Start");
	JButton stop = new JButton("Stop");
	JLabel throughput = new JLabel("Throughput (Cars Per Second): 0");
	Container south = new Container();
	//West container 
	JButton semi = new JButton("Add Semi");
	JButton suv = new JButton("Add SUV");
	JButton sports = new JButton("Add Sports");
	Container west = new Container();
	boolean running = false;
	int carCount = 0;
	long startTime = 0;
	
	public Traffic() {
		// all the basic setup as always

		frame.setSize(1000, 550);
		frame.setLayout(new BorderLayout());
		frame.add(road, BorderLayout.CENTER);
		
		south.setLayout(new GridLayout(1, 3));
		south.add(start);
		start.addActionListener(this);
		south.add(stop);
		stop.addActionListener(this);
		south.add(throughput);
		frame.add(south, BorderLayout.SOUTH);
		
		west.setLayout(new GridLayout(3, 1));
		// add the semi button
		west.add(semi);
		semi.addActionListener(this);
		// add the suv button
		west.add(suv);
		suv.addActionListener(this);
		// add the sports button
		west.add(sports);
		sports.addActionListener(this);
		// add the west container to the frame
		frame.add(west, BorderLayout.WEST);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.repaint();
	}
	
	public static void main(String[] args) {
		new Traffic();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(start)) { // Start the simulation
			if (running == false) { // Only start if it is not running
				running = true; // start
				road.resetCarCount(); // Reset car through put
				startTime = System.currentTimeMillis(); // Reset car through put
				Thread t = new Thread(this);
				t.start();
			}
		}
		if (event.getSource().equals(stop)) { // Stop the simulation
			running = false;
		}
		if (event.getSource().equals(semi)) { // Add semi car to the road
			int new_y_val = 30 + (road.LANE_HEIGHT * random.nextInt(4)); // Pick a random lane
			Semi semi = new Semi(0, new_y_val);
			road.addCar(semi);
			for(int x = 0; x < road.ROAD_WIDTH; x = x + 20) { // for each "spot" in the selected lane
				semi.setX(x);
				if (road.collision(x, new_y_val, semi) == false) { // check if the spot is empty
					frame.repaint();
					return;
				}
			}
		}
		if (event.getSource().equals(suv)) { // Add SUV car to the road
			int new_y_val = 30 + (road.LANE_HEIGHT * random.nextInt(4)); // Pick a random lane
			SUV suv = new SUV(0, new_y_val);
			road.addCar(suv);
			for(int x = 0; x < road.ROAD_WIDTH; x = x + 20) { // for each "spot" in the selected lane
				suv.setX(x);
				if (road.collision(x, new_y_val, suv) == false) { // check if the spot is empty
					frame.repaint();
					return;
				}
			}
		}
		if (event.getSource().equals(sports)) { // Add sports car to the road
			int new_y_val = 30 + (road.LANE_HEIGHT * random.nextInt(4)); // Pick a random lane
			Sports sports = new Sports(0, new_y_val);
			road.addCar(sports);
			for(int x = 0; x < road.ROAD_WIDTH; x = x + 20) { // for each "spot" in the selected lane
				sports.setX(x);
				if (road.collision(x, new_y_val, sports) == false) { // check if the spot is empty
					frame.repaint();
					return;
				}
			}
		}
	}

	@Override
	public void run() { // run the simulation
		while (running == true) { // only if the simulation should running
			road.step(); // move the cars
			carCount = road.getCarCount(); // get the number of cars that have looped the road
			double throughputCalc = carCount / ((double)(System.currentTimeMillis() - startTime) / 1000); // calculate the throughput
			throughput.setText("Throughput (Cars Per Second): "+String.format("%.4g", throughputCalc)); // display the throughput
			frame.repaint(); // repaint the frame
			// modern computers are fast, so we need to slow down the simulation
			// I work with microcontollers so usually I don't have to worry about this.
			// Also, with microcontollers you are usually trying to go as fast as possible
			// for realtime applications so you have to create scheduling libraries, use
			// interrupts, and so on.
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
