package me.xxfreakdevxx.de.game.object.entity;

import java.awt.Graphics;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.gui.texture.GameTexture;
import me.xxfreakdevxx.de.game.object.entity.movement.behavior.EntityBehavior;

public class Chicken extends Entity {
	
	private EntityBehavior behav = null;
	
	public Chicken(Location location) {
		super(location, 26, 20, 12d);
		texture = TextureAtlas.getTexture("chicken");
		this.gTex = new GameTexture(texture, "/assets/{RESOURCE_PACK}/textures/entity/chicken_meta.yml", 6, 47, 44, getUnclonedLocation());
		this.gTex.fps = 2.0;
		setWorld(World.getWorld());
		behav = new EntityBehavior(this);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(gTex.getNextFrame(true), getLocation().getIntX(true), getLocation().getIntY(true), width, height, null);
	}

	@Override
	public void remove() {
		World.getWorld().removeEntity(this);
	}

	@Override
	public void damage(double damage, Entity shooter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		colission.isCollidingButtom();
		colission.isCollidingLeft();
		colission.isCollidingRight();
		colission.isCollidingTop();
		colission.isCollidingMiddle();
		behav.move();
		if(isOnGround == false) fall_distance+= 0.1;
		else fall_distance = 0d;
		movement.move();
		if(getLocation().getY(false) >= 3000) remove();
	}

}
