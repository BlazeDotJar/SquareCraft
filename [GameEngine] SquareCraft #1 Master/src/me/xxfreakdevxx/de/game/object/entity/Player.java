package me.xxfreakdevxx.de.game.object.entity;

import java.awt.Color;
import java.awt.Graphics;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.object.ID;
import me.xxfreakdevxx.de.game.object.entity.movement.EntityMovement;
import me.xxfreakdevxx.de.game.texture.GameTexture;

public class Player extends Entity {
	
	public Player(Location location) {
		super(ID.PLAYER, location, 28, 40, 20.0d);
//		super(ID.PLAYER, location, 15, 45, 20.0d);
		displayname = "L E A";
		movement.isPlayer = true;
		texture = TextureAtlas.getTexture("player_anima");
		gTex = new GameTexture(texture, "/assets/textures/entity/player_anima_meta.yml", 6, 18, 22, getUnclonedLocation());
		gTex.fps = 240000.0;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
//		g.draw3DRect(getLocation().getIntX(true), getLocation().getIntY(true), width, height, true);
		g.drawImage(gTex.getNextFrame(true), getLocation().getIntX(true), getLocation().getIntY(true), width, height, null);
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
