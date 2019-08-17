package me.xxfreakdevxx.de.game.object.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.gui.texture.GameTexture;
import me.xxfreakdevxx.de.game.inventory.Inventory;
import me.xxfreakdevxx.de.game.object.entity.health.HealthIndicator;
import me.xxfreakdevxx.de.game.object.entity.movement.EntityMovement;

public class Player extends Entity {
	
	private HealthIndicator h_bar = null;
	public BufferedImage head = TextureAtlas.getTexture("player_head");
	
	public Player(Location location) {
		super(location, 24, 36, 20.0d);
//		super(ID.PLAYER, location, 24, 36, 20.0d);
		this.displayname = "L E A";
		this.movement.isPlayer = true;
		this.texture = TextureAtlas.getTexture("player_anima");
		this.gTex = new GameTexture(texture, "/assets/{RESOURCE_PACK}/textures/entity/player_anima_meta.yml", 18, 22, getUnclonedLocation());
		this.gTex.fps = 24.0;
		this.h_bar = new HealthIndicator(this);
		this.inventory = new Inventory(16, 4);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
//		g.draw3DRect(getLocation().getIntX(true), getLocation().getIntY(true), width, height, true);
		g.drawImage(gTex.getNextFrame(true), getLocation().getIntX(true)+2, getLocation().getIntY(true)+2, width, height, null);
//		g.drawImage(head, getLocation().getIntX(true)+2+4, getLocation().getIntY(true)+2-10, 20, 30, null);
		colission.render(g);
		renderDisplayname(g);
		h_bar.render(g);
		if(inventory != null) inventory.render(g);
	}

	@Override
	public void remove() {
		world.removeEntity(this);
		world.spawnPlayer();
	}

	@Override
	public void damage(double damage, Entity damager) {
		if(!damage(damage)) System.out.println(this.displayname+" died...");
	}

	@Override
	public void tick() {
		regenerate();
		inventory.tick();
		fall_distance_manager.tick();
		h_bar.tick();
		if(noclip == false) {			
			colission.isCollidingButtom();
			colission.isCollidingLeft();
			colission.isCollidingRight();
			colission.isCollidingTop();
			colission.isCollidingMiddle();
		}
//		if(isOnGround() == false) setFallDistance(this.fall_distance += 0.1);
//		else setFallDistance(0D);
		movement.move();
		checkForNearbyItems();
	}
	
	public EntityMovement getMovement() {
		return movement;
	}

}
