import java.awt.Color;
import java.awt.Graphics;

public class Semi extends Vehicle {
	
	public Semi(int _x, int _y) {
		super(100, 40, 5, _x, _y); // width, height, speed, starting x, starting y
	}
	
	public void paintMe(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
	}
	
}
