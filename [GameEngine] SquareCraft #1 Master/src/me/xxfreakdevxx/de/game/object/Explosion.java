package me.xxfreakdevxx.de.game.object;

import java.awt.image.BufferedImage;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.object.block.Block;
import me.xxfreakdevxx.de.game.object.block.TNTBlock;

public class Explosion {
	
	BufferedImage mask = TextureAtlas.getTexture("explosion_large_mask");
	Location location = null;
	
	public Explosion(Location loc) {
		this.location = loc;
		blast();
	}
	
	public void blast() {
		Location l = location.clone();
		int w = mask.getWidth();
		int h = mask.getHeight();
		for(int xx = 0; xx < w; xx++) {
			for(int yy = 0; yy < h; yy++) {
				if((mask.getRGB(xx, yy)>>24) != 0x00) {
					int pixel = mask.getRGB(xx, yy);
					int red = (pixel >> 16) & 0xff;
					int green = (pixel >> 8) & 0xff;
					int blue = (pixel) & 0xff;
					if(red == 255) ;
					if(green == 255) ;
					if(blue == 255) ;
					l.addAndConvert(xx,yy);
					Block b = World.getWorld().getBlockAt(l.getLocationString());
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
}
