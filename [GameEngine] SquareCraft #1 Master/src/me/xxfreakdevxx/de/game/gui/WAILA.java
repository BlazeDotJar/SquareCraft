package me.xxfreakdevxx.de.game.gui;

import java.awt.Color;
import java.awt.Graphics;

import me.xxfreakdevxx.de.game.MouseInput;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.object.Material;
import me.xxfreakdevxx.de.game.object.block.Block;

public class WAILA {
	
	private static Block block = null;
	private static int mx = 0;
	private static int my = 0;
	private static final int width = 120;
	private static final int height = 45;
	private static int x = ((SquareCraft.windowWidth/2)-(width/2));
	
	public static void displayBlock2(Graphics g) {
		updateMouse();
		if(World.getWorld().isGenerated) {			
			block = World.getWorld().getBlockAt(mx,my);
			if(block.getMaterial() != Material.AIR) {
				x = ((SquareCraft.windowWidth/2)-(width/2));
				g.setColor(new Color(0f,0.3f,0.6f,0.8f));
				g.fillRoundRect(x, 0, SquareCraft.calculateStringWidth(g.getFont(), block.getMaterial().getDisplayname())+8, height, 3, 3); 
				g.setColor(Color.WHITE);
				g.drawString(block.getMaterial().getDisplayname(), x+4, 20);
				g.setColor(new Color(180,180,180));
				g.setFont(g.getFont().deriveFont(2));
				g.drawString("ID: "+block.getMaterial().getId(), x+4, 35);
			}
		}
	}
	static int multiplier = 3;
	public static void displayBlock(Graphics g) {
		int y = (SquareCraft.windowHeight - 10 - ((2*multiplier)*SquareCraft.blocksize));
		updateMouse();
		if(World.getWorld().isGenerated) {			
			block = World.getWorld().getBlockAt(mx,my);
			if(block.getMaterial() != Material.AIR) {
				g.setColor(new Color(0f,0f,0f,0.5f));
				g.fillRect((multiplier * SquareCraft.blocksize)+15, y+10, 130, (multiplier * SquareCraft.blocksize)-20); 
				g.fillRoundRect(5, y-5, (multiplier * SquareCraft.blocksize)+10, (multiplier * SquareCraft.blocksize)+10, 10, 10); 
				g.drawImage(block.getTexture(), 10, y, SquareCraft.blocksize*multiplier, SquareCraft.blocksize*multiplier, null);
				g.setColor(Color.WHITE);
				g.drawString(block.getMaterial().getDisplayname(), 10+SquareCraft.blocksize*multiplier+20, y+25);
				g.setColor(new Color(180,180,180));
				g.setFont(g.getFont().deriveFont(2));
				g.drawString("ID: "+block.getMaterial().getId(), 10+SquareCraft.blocksize*multiplier+20, y+45);
			}
		}
	}
	
	private static void updateMouse() {
		mx = (int)(MouseInput.getInstance().getConvertedX(true, false));
		my = (int)(MouseInput.getInstance().getConvertedY(true, false));
	}
	
}
