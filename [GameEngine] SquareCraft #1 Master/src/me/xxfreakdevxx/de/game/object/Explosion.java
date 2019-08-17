package me.xxfreakdevxx.de.game.object;

import java.awt.image.BufferedImage;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.object.block.Block;
import me.xxfreakdevxx.de.game.object.block.TNTBlock;
import me.xxfreakdevxx.de.game.object.entity.Entity;
import me.xxfreakdevxx.de.game.object.entity.Item;
import me.xxfreakdevxx.de.game.object.entity.movement.Vector;

public class Explosion {
	
	BufferedImage mask_large = TextureAtlas.getTexture("explosion_large_mask");
	BufferedImage mask_medium = TextureAtlas.getTexture("explosion_medium_mask");
	BufferedImage mask_small = TextureAtlas.getTexture("explosion_small_mask");
	Location location = null;
	
	public Explosion(Location loc) {
		this.location = loc;
		blast();
	}
	
	public void blast() {
		int size = SquareCraft.randomInteger(0, 3);
		Location l = location.clone();
		int w = 0;
		int h = 0;
		double explo_x = l.getX(false);
		double explo_y = l.getY(false);
		switch(size) {
		case 0:
			size = 1;
//			w = mask_small.getWidth();
//			h = mask_small.getHeight();
//			for(int xx = 0; xx < w; xx++) {
//				for(int yy = 0; yy < h; yy++) {
//					if((mask_small.getRGB(xx, yy)>>24) != 0x00) {
//						int pixel = mask_small.getRGB(xx, yy);
//						int red = (pixel >> 16) & 0xff;
//						int green = (pixel >> 8) & 0xff;
//						int blue = (pixel) & 0xff;
//						if(red == 255) ;
//						if(green == 255) ;
//						if(blue == 255) ;
//						l.addAndConvert(xx,yy);
//						Block b = World.getWorld().getBlockAt(l.getLocationString());
//						if(b != null) {							
//							if(b.getMaterial() == Material.TNT) {
//								((TNTBlock)b).blastDelay = 10;
//								((TNTBlock)b).blastPeroid = 10;
//								b.interact();
//							}
//							World.getWorld().breakBlockNaturally(l.getLocationString());
//							l = location.clone();
//							l.addAndConvert(-(w/2), -(h/2));
//						}
//					}
//				}
//			}
		case 1:
			w = mask_medium.getWidth();
			h = mask_medium.getHeight();
			for(int xx = 0; xx < w; xx++) {
				for(int yy = 0; yy < h; yy++) {
					if((mask_medium.getRGB(xx, yy)>>24) != 0x00) {
						int pixel = mask_medium.getRGB(xx, yy);
						int red = (pixel >> 16) & 0xff;
						int green = (pixel >> 8) & 0xff;
						int blue = (pixel) & 0xff;
						if(red == 255) ;
						if(green == 255) ;
						if(blue == 255) ;
						l.addAndConvert(xx,yy);
						Block b = World.getWorld().getBlockAt(l.getLocationString());
						if(b != null) {							
							if(b.getMaterial() == Material.TNT) {
								((TNTBlock)b).blastDelay = 10;
								((TNTBlock)b).blastPeroid = 10;
								b.interact();
							}
							World.getWorld().breakBlockNaturally(l.getLocationString());
							l = location.clone();
							l.addAndConvert(-(w/2), -(h/2));
						}
					}
				}
			}
			for(Entity ent : World.getWorld().getEntities()) {
				if(ent instanceof Item == false)  {
					if(ent.getLocation().getX(false) < explo_x) ent.addVelocity(new Vector(-3, 0));
					else if(ent.getLocation().getX(false) > explo_x) ent.addVelocity(new Vector(3, 0));
					if(ent.getLocation().getY(false) < explo_y) ent.addVelocity(new Vector(0, -5));
					else if(ent.getLocation().getY(false) > explo_y) ent.addVelocity(new Vector(0, 5));
				}
			}
			if(World.getWorld().player.getLocation().getX(false) < explo_x) World.getWorld().player.addVelocity(new Vector(-2, 0));
			else if(World.getWorld().player.getLocation().getX(false) > explo_x) World.getWorld().player.addVelocity(new Vector(2, 0));
			if(World.getWorld().player.getLocation().getY(false) < explo_y) World.getWorld().player.addVelocity(new Vector(0, -2));
			else if(World.getWorld().player.getLocation().getY(false) > explo_y) World.getWorld().player.addVelocity(new Vector(0, 2));
			break;
		case 2:
			size = 1;
//			w = mask_large.getWidth();
//			h = mask_large.getHeight();
//			for(int xx = 0; xx < w; xx++) {
//				for(int yy = 0; yy < h; yy++) {
//					if((mask_large.getRGB(xx, yy)>>24) != 0x00) {
//						int pixel = mask_large.getRGB(xx, yy);
//						int red = (pixel >> 16) & 0xff;
//						int green = (pixel >> 8) & 0xff;
//						int blue = (pixel) & 0xff;
//						if(red == 255) ;
//						if(green == 255) ;
//						if(blue == 255) ;
//						l.addAndConvert(xx,yy);
//						Block b = World.getWorld().getBlockAt(l.getLocationString());
//						if(b != null) {							
//							if(b.getMaterial() == Material.TNT) {
//								((TNTBlock)b).blastDelay = 10;
//								((TNTBlock)b).blastPeroid = 10;
//								b.interact();
//							}
//							World.getWorld().breakBlockNaturally(l.getLocationString());
//							l = location.clone();
//							l.addAndConvert(-(w/2), -(h/2));
//						}
//					}
//				}
//			}
//			for(Entity ent : World.getWorld().getEntities()) {
//				if(ent instanceof Item == false)  {
//					if(ent.getLocation().getX(false) < explo_x) ent.addVelocity(new Vector(-3, -5));
//					else if(ent.getLocation().getX(false) > explo_x) ent.addVelocity(new Vector(3, -5));
//				}
//			}
//			if(World.getWorld().player.getLocation().getX(false) < explo_x) World.getWorld().player.addVelocity(new Vector(-3, -5));
//			else if(World.getWorld().player.getLocation().getX(false) > explo_x) World.getWorld().player.addVelocity(new Vector(3, -5));
//			break;
		}
		w = mask_medium.getWidth();
		h = mask_medium.getHeight();
		for(int xx = 0; xx < w; xx++) {
			for(int yy = 0; yy < h; yy++) {
				if((mask_medium.getRGB(xx, yy)>>24) != 0x00) {
					int pixel = mask_medium.getRGB(xx, yy);
					int red = (pixel >> 16) & 0xff;
					int green = (pixel >> 8) & 0xff;
					int blue = (pixel) & 0xff;
					if(red == 255) ;
					if(green == 255) ;
					if(blue == 255) ;
					l.addAndConvert(xx,yy);
					Block b = World.getWorld().getBlockAt(l.getLocationString());
					if(b != null) {							
						if(b.getMaterial() == Material.TNT) {
							((TNTBlock)b).blastDelay = 10;
							((TNTBlock)b).blastPeroid = 10;
							b.interact();
						}
						World.getWorld().breakBlockNaturally(l.getLocationString());
						l = location.clone();
						l.addAndConvert(-(w/2), -(h/2));
					}
				}
			}
		}
		for(Entity ent : World.getWorld().getEntities()) {
			if(ent instanceof Item == false)  {
				if(ent.getLocation().getX(false) < explo_x) ent.addVelocity(new Vector(-3, 0));
				else if(ent.getLocation().getX(false) > explo_x) ent.addVelocity(new Vector(3, 0));
				if(ent.getLocation().getY(false) < explo_y) ent.addVelocity(new Vector(0, -5));
				else if(ent.getLocation().getY(false) > explo_y) ent.addVelocity(new Vector(0, 5));
			}
		}
		if(World.getWorld().player.getLocation().getX(false) < explo_x) World.getWorld().player.addVelocity(new Vector(-2, 0));
		else if(World.getWorld().player.getLocation().getX(false) > explo_x) World.getWorld().player.addVelocity(new Vector(2, 0));
		if(World.getWorld().player.getLocation().getY(false) < explo_y) World.getWorld().player.addVelocity(new Vector(0, -2));
		else if(World.getWorld().player.getLocation().getY(false) > explo_y) World.getWorld().player.addVelocity(new Vector(0, 2));
	}
}
