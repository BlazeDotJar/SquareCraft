package me.xxfreakdevxx.de.game.object.block;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.inventory.ItemStack;
import me.xxfreakdevxx.de.game.object.Explosion;
import me.xxfreakdevxx.de.game.object.Material;
import me.xxfreakdevxx.de.game.object.entity.Item;

public class TNTBlock extends Block {
	
	Color color = null;
	Random random = new Random();
	private boolean isFused = false;
	public int blastDelay = 10;
	public long blastPeroid = 100;
	public TNTBlock(Location location) {
		super(Material.TNT, location);
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
		if(isFused) {
			g.setColor(color);
			g.fillRect(getLocation().getIntX(true), getLocation().getIntY(true), width, height);			
		}
	}
	@Override
	public void tick() {
		
	}
	@Override
	public void interact() {
		if(task == null && timer == null) {
			color = new Color(1f,1f,1f,0.3f);
			isFused = true;
			task = new TimerTask() {
				int count = 0;
				@Override
				public void run() {
					if(count < blastDelay) {
						isFused = !isFused;
					}else {
						World.getWorld().removeBlock(location.getLocationString());
						new Explosion(getLocation(), 9);
						timer.cancel();
						task.cancel();
					}
					count++;
				}
			};
			timer = new Timer();
			timer.schedule(task, 0, blastPeroid);
		}

	}
	@Override
	public Block clone() {
		return new TNTBlock(getLocation());
	}
	@Override
	public void destroy() {
		World.getWorld().spawnEntity(new Item(getLocation().add(SquareCraft.blocksize/2, 0d), new ItemStack(material)));
	}
}
