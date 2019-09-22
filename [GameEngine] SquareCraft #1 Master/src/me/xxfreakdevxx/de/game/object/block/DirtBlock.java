package me.xxfreakdevxx.de.game.object.block;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.inventory.ItemStack;
import me.xxfreakdevxx.de.game.object.Material;
import me.xxfreakdevxx.de.game.object.entity.Item;

public class DirtBlock extends Block {
	
	Color color = null;
	Random random = new Random();
	public DirtBlock(Location location) {
		super(Material.DIRT, location);
		color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(getTexture(), getLocation().getIntX(true), getLocation().getIntY(true), width, height, color, null);
		renderDamage(g);
		
		if(showSelection) {
			g.setColor(selection_color);
			g.fillRect(getLocation().getIntX(true), getLocation().getIntY(true), width, height);
		}
		if(location.getWorld() != null && location.getWorld().showRaster) {
			g.setColor(new Color(0f,0f,0f,0.4f));
			g.drawRect(getLocation().getIntX(true), getLocation().getIntY(true), width, height);
		}
	}
	@Override
	public void tick() {
		
	}
	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Block clone() {
		return new DirtBlock(getLocation());
	}
	@Override
	public void destroy() {
		World.getWorld().spawnEntity(new Item(getLocation().add(0, 0d), new ItemStack(material)));
	}
}
