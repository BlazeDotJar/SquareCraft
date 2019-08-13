package me.xxfreakdevxx.de.game.object;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.gui.texture.GameTexture;

public abstract class GameObject {
	/* GameObject ist ein Object, das von anderen Klassen geerbt werden kann.
	 * Z.b. bei Entities */
	protected Location location;
	public int width=32,height=32;
	public float velX = 0;
	public float velY = 0;
	protected ID id;
	protected BufferedImage texture;
	protected GameTexture gTex = null;
	protected Random ran = new Random();
	protected Timer timer;
	protected TimerTask task;
	
	public GameObject(ID id, Location location, int width, int height) {
		this.id=id;
		this.location=location;
		this.width=width;
		this.height=height;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public Rectangle getBounds() {
		return new Rectangle((int)location.getIntX(true), (int)location.getIntY(true), width, height);
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	
	public GameTexture getGameTexture() {
		return gTex;
	}
	
}
