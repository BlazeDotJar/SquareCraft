package me.xxfreakdevxx.de.game.object.block;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.object.Material;

public class StoneBlock extends Block {
	
	Color color = null;
	Random random = new Random();
	public StoneBlock(Location location) {
		super(Material.STONE, location);
		color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(getTexture(), getLocation().getIntX(true), getLocation().getIntY(true), width, height, color, null);
		
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
}