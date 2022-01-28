/**
 * By: Michael MacDonald
 * Date: 11/8/2021
 * 
 * Game of life
 * Creates a grid of squares. All adjacent cells (including diagonal) are considered neighbors.
 * If a square has 2 or 3 neighbors it survives to next round else it dies.
 * If a dead square has 3 neighbors it becomes alive again
 * You can set the squares as dead or alive by clicking on them before you start the simulation
 * You can step through the rounds one by one using "step"
 * You can have it automatically step every 500 ms using "start"
 * You can stop the auto step using "stop"
 */






import javax.swing.JFrame;
// import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.Container;
import java.awt.GridLayout;

public class Life implements MouseListener, ActionListener, Runnable {

	boolean[][] cells = new boolean[25][25]; // holds state of each cell (functional cells array)
	JFrame frame = new JFrame("life sim"); // frame
	LifePanel panel = new LifePanel(cells); // panel to hold cells
	Container south =  new Container(); // container to hold buttons
	JButton step = new JButton("Step"); // buttons
	JButton start = new JButton("Start");
	JButton stop = new JButton("Stop");

	boolean running = false; // is running?

	public Life() {
		// frame
		frame.setSize(600,600);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		panel.addMouseListener(this);

		// buttons
		south.setLayout(new GridLayout(1,3));
		south.add(step);
		step.addActionListener(this);
		south.add(start);
		start.addActionListener(this);
		south.add(stop);
		stop.addActionListener(this);
		frame.add(south, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Life();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) { // if a square it clicked change state
		if (!running) { // Only allow clicking if the game is paused
			System.out.println(e.getX() + ", " + e.getY());
			double width = (double)panel.getWidth() / cells[0].length;
			double height = (double)panel.getHeight() / cells.length;
			int column = Math.min(cells[0].length, (int)(e.getX() / width));
			int row = Math.min(cells.length, (int)(e.getY() / height));
			System.out.println(column + ", " + row);
			cells[row][column] = !cells[row][column];
			frame.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) { // if button is pressed
		if (e.getSource().equals(step)) { // step through simulation
			step();
		}
		if (e.getSource().equals(start)) { // start running sim
			if (!running) {
				running = true;
				Thread t = new Thread(this);
				t.start();
			}
		}
		if (e.getSource().equals(stop)) { // stop running sim
			if (running) {
				running = false;
			}
		}
	}

	@Override
	public void run() { // new thread to run auto steps without affecting the rest of the program
		while (running) { // run sim by automatically stepping through every 500 ms
			step();
			try {
				Thread.sleep(500);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void step() { // step through sim
		boolean[][] nextCells = new boolean[cells.length][cells[0].length];
		int rowLength = cells.length - 1; // Account for the fact that length starts at 1 not 0
		int colLength = cells[0].length - 1; // Account for the fact that length starts at 1 not 0

		for (int row = 0; row < cells.length; row++) {
			for (int column = 0; column < cells[0].length; column++) {
				int neighborCount = 0; // to hold the number of neighbors

				if (row>0 && column>0 && cells[row-1][column-1]) { // Upper left
					neighborCount++;
				}
				if (row>0 && cells[row-1][column]) { // Upper center
					neighborCount++;
				}
				if (row>0 && column<colLength && cells[row-1][column+1]) { // Upper right
					neighborCount++;
				}

				if (column>0 && cells[row][column-1]) { // Left
					neighborCount++;
				}
				if (column<colLength && cells[row][column+1]) { // Right
					neighborCount++;
				}

				if (row<rowLength && column>0 && cells[row+1][column-1]) { // Lower left
					neighborCount++;
				}
				if (row<rowLength && cells[row+1][column]) { // Lower
					neighborCount++;
				}
				if (row<rowLength && column<colLength && cells[row+1][column+1]) { // Lower right
					neighborCount++;
				}

				if (cells[row][column]) { // Currently alive
					if (neighborCount == 2 || neighborCount == 3) { // Keep cell alive
						nextCells[row][column] = true;
					} else { // Kill cell
						nextCells[row][column] = false;
					}
				} else { // Currently dead
					if (neighborCount == 3) { // Make cell alive
						nextCells[row][column] = true;
					} else { // Stay dead
						nextCells[row][column] = false;
					}
				}

			} // end of nested for loop
		} // end of for loop

		cells = nextCells; // Update functional cells
		panel.setCells(nextCells); // Update visual cells
		frame.repaint(); // repaint
	}

}