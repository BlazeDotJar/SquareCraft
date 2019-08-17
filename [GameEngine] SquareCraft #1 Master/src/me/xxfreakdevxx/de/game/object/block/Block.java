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
	public Block(Material material, Location location) {
		super(location, SquareCraft.blocksize, SquareCraft.blocksize);
		this.material = material;
		this.texture = TextureAtlas.getTexture(material.getName());
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void interact();
	public abstract void destroy();
	public abstract Block clone();
	
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
