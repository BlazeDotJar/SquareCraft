package me.xxfreakdevxx.de.game;

import java.awt.Rectangle;

import me.xxfreakdevxx.de.game.object.GameObject;

public class Camera {
	private float x=0,y=0;
	private float move_speed = 10f;
	public Camera(float x, float y) {
		this.x=x;
		this.y=y;
	}
	public Camera(Location location) {
		this.x=(float)location.getX(false);
		this.y=(float)location.getY(false);
	}
	
	public void tick(GameObject object) {
		x += (((object.getLocation().getX(false) - x) - SquareCraft.windowWidth/2) + (object.width/2)) * 0.05f;
		y += ((object.getLocation().getY(false) - y) - SquareCraft.windowHeight/2) * 0.05f;
//		x += ((object.getLocation().getX(false) - x) - SquareCraft.windowWidth/2);
//		y += ((object.getLocation().getY(false) - y) - SquareCraft.windowHeight/2);
	
//		if(x <= 0) x = 0; /* Border Links */
//		if(x >= (SquareCraft.windowWidth + 32)) x = (SquareCraft.windowWidth+32); /* Border Rechts */
//		if(y <= 0) y = 0; /* Border Oben */
//		if(x >= (SquareCraft.windowHeight + 16)) x = (SquareCraft.windowHeight+16); /* Border unten */
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,SquareCraft.windowWidth, SquareCraft.windowHeight);
	}
	
	public float getX() {
		return x;
	}
	public int getX(boolean fixToRaster) {	
		if(fixToRaster) return (int)(x/SquareCraft.blocksize)*SquareCraft.blocksize;
		else return (int)x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}
	public int getY(boolean fixToRaster) {
		if(fixToRaster) return (int)(y/SquareCraft.blocksize)*SquareCraft.blocksize;
		else return (int)y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void addX(int direction) {
		x+=move_speed * direction;
//		x+=SquareCraft.blocksize * direction;
	}
	public void addY(int direction) {
		y+=move_speed * direction;
//		y+=SquareCraft.blocksize * direction;
	}
	
}
