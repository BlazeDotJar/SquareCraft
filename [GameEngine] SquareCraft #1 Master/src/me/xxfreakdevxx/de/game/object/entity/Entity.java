package me.xxfreakdevxx.de.game.object.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.inventory.Inventory;
import me.xxfreakdevxx.de.game.object.GameObject;
import me.xxfreakdevxx.de.game.object.entity.movement.ColissionDetector;
import me.xxfreakdevxx.de.game.object.entity.movement.EntityMovement;
import me.xxfreakdevxx.de.game.object.entity.movement.Vector;

public abstract class Entity extends GameObject {
	
	/* Variables */
	protected int timeLived = 0;
	public String displayname = "LivingEntity";/* Max 12 Chars */
	protected final int damageIndicatorCooldown = 3;
	protected boolean showIndicator = false;
	protected int indicatorTime = 0;
	protected double health = 0D;
	protected double maxHealth = 0D;
	protected boolean isDead = false;
	protected double fall_distance = 0d;
	protected boolean isOnGround = false;
	public boolean noclip = false;
	public boolean flipTextureLeft = true;
	public boolean flipTextureRight = true;
	protected Vector velocity = new Vector(0d,0d);
	public Inventory inventory = null;
	public int pickupRange = 3*SquareCraft.blocksize;
	
	/* Health Regenerating */
	private int regenerating_ticks = 0;
	private final int regenerating_speed = 240;
	private int regenerating_delay = 600;
	private boolean hasDelay = true;
	
	protected ColissionDetector colission = null;
	public EntityMovement movement = null;
	protected World world = null;
	protected FallDistanceManager fall_distance_manager = null;
	
	
	public Entity(Location location, int width, int height, double health) {
		super(location, width, height);
		this.world = location.getWorld();
		this.maxHealth=health;
		this.health=health;
		this.movement = new EntityMovement(this);
		this.colission = new ColissionDetector(this);
		this.fall_distance_manager = new FallDistanceManager(this);
	}

	
	public abstract void render(Graphics g);
	public abstract void remove();
	public abstract void damage(double damage, Entity shooter);
	
	public void addTime() {
		timeLived++;
	}
	
	public void regenerate() {
		if(health < maxHealth) {
			if(hasDelay == true) {
				if(regenerating_ticks == regenerating_delay) {				
					hasDelay = false;
					regenerating_ticks = 0;
				}
			}else regenerating_ticks++;
			if(hasDelay == false){
				if(regenerating_ticks == regenerating_speed) {
					health += 1;
					regenerating_ticks = 0;
				}
			}else regenerating_ticks++;
		}
	}
	public void renderDisplayname(Graphics g) {
		g.setFont(new Font("consolas", 0, 14));
		g.setColor(new Color(0f,0f,0f,0.4f));
		g.fillRect((getLocation().getIntX(true)-(SquareCraft.calculateStringWidth(g.getFont(), displayname)/2)+10)-2, (int) (getLocation().getIntY(true)-19), SquareCraft.calculateStringWidth(g.getFont(), displayname)+4, 16);
//		g.fillRect((getLocation().getIntX(true)+(width/2) - 50), (int) (getLocation().getIntY(true)-19-healthbarHeight), 100, 16);
		g.setColor(Color.WHITE);
		g.drawString(displayname, (getLocation().getIntX(true)-(SquareCraft.calculateStringWidth(g.getFont(), displayname)/2)+10), getLocation().getIntY(true)-7);
	}
	
	public void addVelocity(Vector vector) {
		this.velocity.add(vector);
	}
	public Vector getVelocity() {
		return velocity;
	}
	
	/**
	 * Fügt der Entität Schaden hinzu
	 * @param damage
	 * @return
	 */
	public boolean damage(double damage) {
		if(health - damage < 0.1d){
			remove();
			world.spawnPlayer();
			return false;
		}else health-=damage;
		hasDelay = true;
		regenerating_ticks = 0;
//		texture = tint(texture);
		return true;
	}
//	public static BufferedImage tint(BufferedImage img) {
//
//	    for (int x = 0; x < img.getWidth(); x++) {
//	        for (int y = 0; y < img.getHeight(); y++) {
//	        	if( (img.getRGB(x, y)>>24) != 0x00 ) {	        		
////	        		Color color = new Color(img.getRGB(x, y));
//	        		
//	        		// do something with the color :) (change the hue, saturation and/or brightness)
//	        		// float[] hsb = new float[3];
//	        		// Color.RGBtoHSB(color.getRed(), old.getGreen(), old.getBlue(), hsb);
//	        		
//	        		// or just call brighter to just tint it
//	        		Color brighter = new Color(1f,0f,0f,0.3f);
//	        		
//	        		img.setRGB(x, y, brighter.getRGB());
//	        	}
//	        }
//	    }
//		return img;
//	}

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
		return maxHealth;
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
		if(distance == 0D) {
			velocity.x = 0d;
			velocity.y = 0d;
		}
	}
	
	private double x1 = 0;
	private double y1 = 0;
	private double x2 = 0;
	private double y2 = 0;
	public void checkForNearbyItems() {
		for(Entity ent : world.getEntities()) {
			if(ent instanceof Item) {
				if(getLocation().getIntX(true) > ent.getLocation().getX(true)) {
					x1 = getLocation().getX(true);
					x2 = ent.getLocation().getX(true);
				}else  {
					x1 = ent.getLocation().getX(true);
					x2 = getLocation().getX(true);
				}
				if(getLocation().getIntY(true) > ent.getLocation().getY(true)) {
					y1 = getLocation().getY(true);
					y2 = ent.getLocation().getY(true);
				}else  {
					y1 = ent.getLocation().getY(true);
					y2 = getLocation().getY(true);
				}
				
				if(x1 - x2 <= pickupRange && y1 - y2 <= pickupRange && ent.timeLived >= ((Item)ent).pickupDelay && inventory.addItem(((Item)ent).getItemStack()) == true) {
					World.getWorld().removeEntity(ent);
				}
			}
		}
	}
	public double getPickupRange() {
		return pickupRange;
	}
	
	public void setOnGround(boolean onGround) {
		if(isOnGround == true && onGround == false) fall_distance_manager.allowFiltering();
		else if(isOnGround == false && onGround == true) fall_distance_manager.disableFiltering();
		
		if(isOnGround == false && onGround == true && fall_distance_manager.measure()) {
			double damage = ((fall_distance-fall_distance_manager.min_distance) * world.physics.damage_per_distance);
			damage(damage);
		}
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
	
	public class FallDistanceManager {
		
		public int prev_y = -999;
		public int cur_y = 0;
		private Entity target = null;
		public boolean isOnGround = false;
		private double min_distance = 4.5d;
		private boolean filter_highest = true;
		
		public FallDistanceManager(Entity target) {
			this.target = target;
		}
		
		public void tick() {
			cur_y = target.getLocation().getBlockIntY();
			if(filter_highest) {
				if(prev_y == -999  || target.getLocation().getBlockIntY() < prev_y) prev_y = target.getLocation().getBlockIntY();
			}
		}
		
		public boolean measure() {
			if((distance(cur_y, prev_y)) >= min_distance) {
				return true;
			}
			return false;
		}
		public void allowFiltering() {
			prev_y = -999;
			filter_highest = true;
		}
		public void disableFiltering() {
			filter_highest = false;
		}
		public double distance(double a, double b) {
			if(a > b) return (a-b)+1;
			else return (b - a)+1;
		}
		public int distance(int a, int b) {
			return (int) distance((double)a,(double)b);
		}
	}
	
}
