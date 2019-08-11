package me.xxfreakdevxx.de.game.object.entity;

import java.awt.Graphics;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.object.ID;
import me.xxfreakdevxx.de.game.object.entity.movement.behavior.EntityBehavior;
import me.xxfreakdevxx.de.game.texture.GameTexture;

public class Pig extends Entity {
	
	private EntityBehavior behav = null;
	
	public Pig(Location location) {
		super(ID.PIG, location, 28, 22, 12d);
		texture = TextureAtlas.getTexture("pig_anima");
		this.gTex = new GameTexture(texture, "/assets/textures/entity/pig_anima_meta.yml", 6, 40, 34, getUnclonedLocation());
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
		World.getWorld().entities.remove(this);
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
