package me.xxfreakdevxx.de.game.object.block;

import java.awt.Color;
import java.awt.Graphics;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.object.GameObject;
import me.xxfreakdevxx.de.game.object.Material;

public abstract class Block extends GameObject {
	
	protected Material material;
	protected boolean showSelection = false;
	protected Color selection_color = new Color(1f,1f,0f,0.1f);
	protected int chunk_id = 0;
	public int health = 0;// Block Health regelt, ob ein Block per TNT zerstört wird oder nicht
	public int max_health = 0;// Block Health regelt, ob ein Block per TNT zerstört wird oder nicht
	public int ten_percent = 0;
	
	public Block(Material material, Location location) {
		super(location, SquareCraft.blocksize, SquareCraft.blocksize);
		this.material = material;
		this.texture = TextureAtlas.getTexture(material.getName());
		this.health = material.getHealth();
		this.max_health = material.getHealth();
		this.ten_percent = max_health / 10;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void interact();
	public abstract void destroy();
	public abstract Block clone();
	
	protected void renderDamage(Graphics g) {
		/* 90% Schaden */if(health < (max_health*0.1)) g.drawImage(TextureAtlas.getTexture("damage_stage_9"), getUnclonedLocation().getIntX(true), getUnclonedLocation().getIntY(true), width, height, null);
		/* 80% Schaden */else if(health < (max_health*0.2)) g.drawImage(TextureAtlas.getTexture("damage_stage_8"), getUnclonedLocation().getIntX(true), getUnclonedLocation().getIntY(true), width, height, null);
		/* 70% Schaden */else if(health < (max_health*0.3)) g.drawImage(TextureAtlas.getTexture("damage_stage_7"), getUnclonedLocation().getIntX(true), getUnclonedLocation().getIntY(true), width, height, null);
		/* 60% Schaden */else if(health < (max_health*0.4)) g.drawImage(TextureAtlas.getTexture("damage_stage_6"), getUnclonedLocation().getIntX(true), getUnclonedLocation().getIntY(true), width, height, null);
		/* 50% Schaden */else if(health < (max_health*0.5)) g.drawImage(TextureAtlas.getTexture("damage_stage_5"), getUnclonedLocation().getIntX(true), getUnclonedLocation().getIntY(true), width, height, null);
		/* 40% Schaden */else if(health < (max_health*0.6)) g.drawImage(TextureAtlas.getTexture("damage_stage_4"), getUnclonedLocation().getIntX(true), getUnclonedLocation().getIntY(true), width, height, null);
		/* 30% Schaden */else if(health < (max_health*0.7)) g.drawImage(TextureAtlas.getTexture("damage_stage_3"), getUnclonedLocation().getIntX(true), getUnclonedLocation().getIntY(true), width, height, null);
		/* 20% Schaden */else if(health < (max_health*0.8)) g.drawImage(TextureAtlas.getTexture("damage_stage_2"), getUnclonedLocation().getIntX(true), getUnclonedLocation().getIntY(true), width, height, null);
		/* 10% Schaden */else if(health < (max_health*0.9)) g.drawImage(TextureAtlas.getTexture("damage_stage_1"), getUnclonedLocation().getIntX(true), getUnclonedLocation().getIntY(true), width, height, null);
		/*  0% Schaden */else if(health < (max_health*0.99)) g.drawImage(TextureAtlas.getTexture("damage_stage_0"), getUnclonedLocation().getIntX(true), getUnclonedLocation().getIntY(true), width, height, null);
		if(health != max_health) {
		}
	}
	
	public Material getMaterial() {
		if(material == null) return Material.AIR;
		else return material;
	}
	public void select() {
		this.showSelection = !this.showSelection;
	}
	public void setChunk(int id) {
		chunk_id = id;
	}

}
