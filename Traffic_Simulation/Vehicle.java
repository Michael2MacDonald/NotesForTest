import java.awt.Graphics;

abstract public class Vehicle {

	final int width, height, speed;
	int x, y; // position
	
	public Vehicle(int _width, int _height, int _speed, int _x, int _y) { // constructor; set car's initial position
		width = _width;
		height = _height;
		speed = _speed;
		x = _x;
		y = _y;
	}
	
	abstract public void paintMe(Graphics g); // paint the car (abstract = pure virtual function = must be implemented in derived classes = this class can not be instantiated)
	
	public int getX() {
		return x;
	}
	
	public void setX(int newx) {
		x = newx;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int newy) {
		y = newy;
	}
	
	public int getWidth() {
		return width;
	}
	
}
