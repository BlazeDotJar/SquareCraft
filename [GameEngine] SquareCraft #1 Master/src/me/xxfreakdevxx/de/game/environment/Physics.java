package me.xxfreakdevxx.de.game.environment;

public class Physics {
	
	private World world = null;
	private double gravity_strength = 1.2d;
	private double gravity_max_strength = 10d;
	public boolean allow_gravity = true;
	
	public Physics(World world) {
		this.world = world;
	}
	
	public void applyGravity() {
		if(allow_gravity) {			
			if(gravity_strength*world.player.getFallDistance() >= gravity_max_strength) world.player.getMovement().addGravity(gravity_max_strength);
			else world.player.getMovement().addGravity(gravity_strength * world.player.getFallDistance());
		}
	}
	
}
