package me.xxfreakdevxx.de.game.object.entity;

import java.awt.Color;
import java.awt.Graphics;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.inventory.ItemStack;
import me.xxfreakdevxx.de.game.object.Material;

public class Item extends Entity {
	
	private ItemStack item = null;
	int pickupDelay = 150;
	
	public Item(Location location, ItemStack item) {
		super(location, 10, 10, 99999999D);
		this.item = item;
		this.texture = item.getMaterial().getTexture();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect((int)getUnclonedLocation().getX(true)-1, (int)getUnclonedLocation().getY(true)-1, width+1, height+1);
		g.drawImage(item.getMaterial().getTexture(), (int)getUnclonedLocation().getX(true), (int)getUnclonedLocation().getY(true), width, height, null);
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
		return item.getMaterial();
	}

	public ItemStack getItemStack() {
		return item;
	}

}
