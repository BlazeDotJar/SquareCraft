package me.xxfreakdevxx.de.game.object.entity;

import java.awt.Color;
import java.awt.Graphics;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.object.ID;
import me.xxfreakdevxx.de.game.object.entity.movement.EntityMovement;

public class Player extends Entity {
	
	public Player(Location location) {
		super(ID.PLAYER, location, 13, 29, 20.0d);
//		super(ID.PLAYER, location, 15, 45, 20.0d);
		displayname = "L E A";
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.draw3DRect(getLocation().getIntX(true), getLocation().getIntY(true), width, height, true);
		renderDisplayname(g);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
	}

	@Override
	public void damage(double damage, Entity shooter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		if(noclip == false) {			
			colission.isCollidingButtom();
			colission.isCollidingLeft();
			colission.isCollidingRight();
			colission.isCollidingTop();
			colission.isCollidingMiddle();
		}
		if(isOnGround == false) fall_distance+= 0.1;
		else fall_distance = 0d;
		movement.move();
	}
	
	public EntityMovement getMovement() {
		return movement;
	}

}
