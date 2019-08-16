package me.xxfreakdevxx.de.game.environment;

import me.xxfreakdevxx.de.game.object.entity.Entity;

public class Physics {
	
	private World world = null;
	private double gravity_strength = 1.2d;
	private double gravity_max_strength = 10d;
	public boolean allow_gravity = true;
	public double damage_per_distance = 0.5D;
	
	public Physics(World world) {
		this.world = world;
	}
	
	public void applyGravity() {
		if(allow_gravity)
			if(gravity_strength*world.player.getFallDistance() >= gravity_max_strength) world.player.getMovement().addGravity(gravity_max_strength);
			else world.player.getMovement().addGravity(gravity_strength * world.player.getFallDistance());
		for(Entity ent : World.getWorld().entities) {
			if(gravity_strength*ent.getFallDistance() >= gravity_max_strength)
				ent.movement.addGravity(gravity_max_strength);
			else ent.movement.addGravity(gravity_strength * ent.getFallDistance());			
		}
	}
	
}
