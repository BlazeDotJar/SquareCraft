package me.xxfreakdevxx.de.game.object;

import java.awt.image.BufferedImage;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.object.block.Block;
import me.xxfreakdevxx.de.game.object.block.TNTBlock;

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
		switch(size) {
		case 0:
			w = mask_small.getWidth();
			h = mask_small.getHeight();
			for(int xx = 0; xx < w; xx++) {
				for(int yy = 0; yy < h; yy++) {
					if((mask_small.getRGB(xx, yy)>>24) != 0x00) {
						int pixel = mask_small.getRGB(xx, yy);
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
							World.getWorld().removeBlock(l.getLocationString());
							l = location.clone();
							l.addAndConvert(-(w/2), -(h/2));
						}
					}
				}
			}
			break;
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
							World.getWorld().removeBlock(l.getLocationString());
							l = location.clone();
							l.addAndConvert(-(w/2), -(h/2));
						}
					}
				}
			}
			break;
		case 2:
			w = mask_large.getWidth();
			h = mask_large.getHeight();
			for(int xx = 0; xx < w; xx++) {
				for(int yy = 0; yy < h; yy++) {
					if((mask_large.getRGB(xx, yy)>>24) != 0x00) {
						int pixel = mask_large.getRGB(xx, yy);
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
							World.getWorld().removeBlock(l.getLocationString());
							l = location.clone();
							l.addAndConvert(-(w/2), -(h/2));
						}
					}
				}
			}
			break;
		}
	}
}
