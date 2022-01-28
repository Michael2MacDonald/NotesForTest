import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;


public class LifePanel extends JPanel {
	boolean[][] cells; // holds the state of all cells for writing to the screen (different array from the functional cells array but should hold the same values)
	double width; // width of a cells in px
	double height; // height of a cells in px

	public LifePanel(boolean[][] in){
		cells = in; // set value of cells to value of the functional cells array
	}

	public void setCells(boolean[][] newCells) { // called to update the non-functional visual cells array
		cells = newCells; // Update cells
	}

	public void paintComponent(Graphics g){ // paint grid and squares
		super.paintComponent(g);
		width = (double)this.getWidth() / cells[0].length;
		height = (double)this.getHeight() / cells.length;

		// Paint squares
		g.setColor(Color.BLUE);
		for (int row = 0; row < cells.length; row++) {
			for (int column = 0; column < cells[0].length; column++) {
				if (cells[row][column]) {
					g.fillRect((int)Math.round(column*width), (int)Math.round(row*height), (int)width+1, (int)height+1);
				}
			}
		}

		// Paint grid
		g.setColor(Color.BLACK);
		for (int i = 0; i < cells[0].length + 1; i++) { // rows
			g.drawLine((int)Math.round(i*width), 0, (int)Math.round(i*width), this.getHeight());
		}
		for (int i = 0; i < cells.length + 1; i++) { // columns
			g.drawLine(0, (int)Math.round(i*height), this.getWidth(), (int)Math.round(i*height));
		}
	}

}
