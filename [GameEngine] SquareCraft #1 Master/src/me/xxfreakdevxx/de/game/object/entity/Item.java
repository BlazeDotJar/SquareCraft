package me.xxfreakdevxx.de.game.object.entity;

import java.awt.Color;
import java.awt.Graphics;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.object.Material;

public class Item extends Entity {
	
	private Material material = Material.AIR;
	
	public Item(Location location, Material material) {
		super(location, 20, 20, 99999999D);
		this.material = material;
		this.texture = material.getTexture();
		System.out.println("Location: "+location.getLocationString());
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect((int)location.getX(true)-1, (int)location.getY(true)-1, width+1, height+1);
		g.drawImage(material.getTexture(), (int)location.getX(true), (int)location.getY(true), width, height, null);
		g.setColor(new Color(1f,1f,1f,0.1f));
		g.fillRect((int)location.getX(true)-1, (int)location.getY(true)-1, width+1, height+1);
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
		movement.move();
	}

}
