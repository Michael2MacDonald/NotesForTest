import java.awt.Color;
import java.awt.Graphics;

public class SUV extends Vehicle {

	public SUV(int _x, int _y) {
		super(60, 30, 8, _x, _y); // width, height, speed, starting x, starting y
	}
	
	public void paintMe(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, width, height);
	}
	
}
