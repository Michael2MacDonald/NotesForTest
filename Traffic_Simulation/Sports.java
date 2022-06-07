import java.awt.Color;
import java.awt.Graphics;

public class Sports extends Vehicle {

	public Sports(int _x, int _y) {
		super(40, 20, 12, _x, _y); // width, height, speed, starting x, starting y
	}
	
	public void paintMe(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
	}

}
