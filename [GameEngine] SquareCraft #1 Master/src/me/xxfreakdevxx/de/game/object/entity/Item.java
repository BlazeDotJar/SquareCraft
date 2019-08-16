package me.xxfreakdevxx.de.game.object.entity;

import java.awt.Color;
import java.awt.Graphics;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.object.Material;

public class Item extends Entity {
	
	private Material material = Material.AIR;
	
	public Item(Location location, Material material) {
		super(location, 20, 20, 99999999D);
		this.material = material;
		this.texture = material.getTexture();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		System.out.println("X/Y: "+getUnclonedLocation().getX(true)+"");
		g.drawRect((int)getUnclonedLocation().getX(true)-1, (int)getUnclonedLocation().getY(true)-1, width+1, height+1);
		g.drawImage(material.getTexture(), (int)getUnclonedLocation().getX(true), (int)getUnclonedLocation().getY(true), width, height, null);
		g.setColor(new Color(1f,1f,1f,0.1f));
		g.fillRect((int)getUnclonedLocation().getX(true)-1, (int)getUnclonedLocation().getY(true)-1, width+1, height+1);
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
		movement.move();
	}
	
	public Material getMaterial() {
		return material;
	}

}
