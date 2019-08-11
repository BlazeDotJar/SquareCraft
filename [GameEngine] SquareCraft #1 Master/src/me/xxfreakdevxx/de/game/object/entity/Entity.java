package me.xxfreakdevxx.de.game.object.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.object.GameObject;
import me.xxfreakdevxx.de.game.object.ID;
import me.xxfreakdevxx.de.game.object.entity.movement.ColissionDetector;
import me.xxfreakdevxx.de.game.object.entity.movement.EntityMovement;

public abstract class Entity extends GameObject {
	
	/* Variables */
	protected final int damageIndicatorCooldown = 3;
	protected boolean showIndicator = false;
	protected int indicatorTime = 0;
	protected double health = 20.0D;
	protected double maxHealth = 20.0D;
	protected boolean isDead = false;
	protected double fall_distance = 0d;
	protected boolean isOnGround = false;
	public boolean noclip = false;
	public boolean flipTextureLeft = true;
	public boolean flipTextureRight = true;
	
	protected ColissionDetector colission = null;
	public EntityMovement movement = null;
	protected World world = null;
	
	
	public Entity(ID id, Location location, int width, int height, double health) {
		super(id, location, width, height);
		this.maxHealth=health;
		this.health=health;
		this.movement = new EntityMovement(this);
		this.colission = new ColissionDetector(this);
	}

	
	public abstract void render(Graphics g);
	public abstract void remove();
	public abstract void damage(double damage, Entity shooter);
	
	/* Healthbar */
	private double healthbarPrefferedLength = 100;
	private double healthbarHeight = 3;
	private Color healthColorFriendly = new Color(21, 221, 17);
	private Color healthColorEnemy = new Color(235, 19, 22);
	public String displayname = "LivingEntity";/* Max 12 Chars */
	private int healthbarX = 0;
	private int healthbarY = 0;
	public void renderHealthbar(Graphics g) {
		healthbarX = (int) ((getLocation().getIntX(true) + (width/2)) - (healthbarPrefferedLength/2));
		healthbarY = (int) (getLocation().getIntY(true) - (healthbarHeight));
		double one = maxHealth / 100;
		double total = health / one;
		g.setColor(Color.BLACK);
		g.fillRect(healthbarX, healthbarY, (int) healthbarPrefferedLength, (int) healthbarHeight);
		if(getId() == ID.PLAYER) g.setColor(healthColorFriendly);
		if(getId() == ID.ENEMY) g.setColor(healthColorEnemy);
		g.fillRect(healthbarX, healthbarY, (int) ((int) total), (int) healthbarHeight);
	}
	public void renderDisplayname(Graphics g) {
		g.setFont(new Font("consolas", 0, 14));
		g.setColor(new Color(0f,0f,0f,0.4f));
		g.fillRect((getLocation().getIntX(true)-(SquareCraft.calculateStringWidth(g.getFont(), displayname)/2)+10)-2, (int) (getLocation().getIntY(true)-19-healthbarHeight), SquareCraft.calculateStringWidth(g.getFont(), displayname)+4, 16);
//		g.fillRect((getLocation().getIntX(true)+(width/2) - 50), (int) (getLocation().getIntY(true)-19-healthbarHeight), 100, 16);
		g.setColor(Color.WHITE);
		g.drawString(displayname, (getLocation().getIntX(true)-(SquareCraft.calculateStringWidth(g.getFont(), displayname)/2)+10), getLocation().getIntY(true)-7-(int)healthbarHeight);
	}

	public Location getLocation() {
		return location.clone();
	}
	public Location getUnclonedLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}
	
	public double getMaxHealth() {
		return health;
	}

	public void setMaxHealth(double health) {
		this.health = health;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public double getFallDistance() {
		return fall_distance;
	}
	
	public void setFallDistance(double distance) {
		this.fall_distance = distance;
	}
	
	public void setOnGround(boolean onGround) {
		this.isOnGround = onGround;
	}
	
	public boolean isOnGround() {
		return isOnGround;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}
	
	public ColissionDetector getColission() {
		return colission;
	}
	
}
