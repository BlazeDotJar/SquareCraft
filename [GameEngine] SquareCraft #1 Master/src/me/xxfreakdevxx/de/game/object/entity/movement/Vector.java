package me.xxfreakdevxx.de.game.object.entity.movement;

import me.xxfreakdevxx.de.game.Location;

public class Vector {
	
	public double x = 0;
	public double y = 0;
	
	public Vector(double x, double y) {
		this.x=x;
		this.y=y;
	}
	
	public Location addToLocation(Location location) {
		roundCoords();
		location.add(x,y);
//		if(x < -0.05) x+=0.05d;
//		else if(x > 0.05) x-=0.05d;
//		if(y < -0.05) y+=0.05d;
//		else if(y > 0.05) y-=0.05d;
		return location;
	}
	
	public void add(double x, double y) {
		this.x+=x;
		this.y+=y;
	}
	
	public void subtract(double x, double y) {
		this.x-=x;
		this.y-=y;
	}

	public void add(Vector vector) {
		this.x += vector.x;
		this.y += vector.y;
	}
	
	private void roundCoords() {
		x -= x % 0.01;
		x *= 10d;
		x = ((int)x) / 10d;
		y -= y % 0.01;
		y *= 10d;
		y = ((int)y) / 10d;
	}
	
}
