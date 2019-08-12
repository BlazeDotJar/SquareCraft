package me.xxfreakdevxx.de.game.object.entity;

import java.awt.Color;
import java.awt.Graphics;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.object.ID;
import me.xxfreakdevxx.de.game.object.entity.health.HealthIndicator;
import me.xxfreakdevxx.de.game.object.entity.movement.EntityMovement;
import me.xxfreakdevxx.de.game.texture.GameTexture;

public class Player extends Entity {
	
	private HealthIndicator h_bar = null;
	
	public Player(Location location) {
		super(ID.PLAYER, location, 28, 40, 20.0d);
//		super(ID.PLAYER, location, 15, 45, 20.0d);
		this.displayname = "L E A";
		this.movement.isPlayer = true;
		this.texture = TextureAtlas.getTexture("player_anima");
		this.gTex = new GameTexture(texture, "/assets/textures/entity/player_anima_meta.yml", 6, 18, 22, getUnclonedLocation());
		this.gTex.fps = 240000.0;
		this.h_bar = new HealthIndicator(this);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.draw3DRect(getLocation().getIntX(true), getLocation().getIntY(true), width, height, true);
		g.drawImage(gTex.getNextFrame(true), getLocation().getIntX(true)+2, getLocation().getIntY(true), width, height, null);
		colission.render(g);
		renderDisplayname(g);
		h_bar.render(g);
	}

	@Override
	public void remove() {
		world.entities.remove(this);
	}

	@Override
	public void damage(double damage, Entity damager) {
		if(!damage(damage)) System.out.println(this.displayname+" died...");
	}

	@Override
	public void tick() {
		fall_distance_manager.tick();
		h_bar.tick();
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
