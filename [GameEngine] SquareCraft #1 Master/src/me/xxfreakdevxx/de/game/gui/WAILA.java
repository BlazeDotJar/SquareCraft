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
	
	public static void displayBlock(Graphics g) {
		updateMouse();
		if(World.getWorld().isGenerated) {			
			block = World.getWorld().getBlockAt(mx,my);
			if(block.getMaterial() != Material.AIR) {
				x = ((SquareCraft.windowWidth/2)-(width/2));
				g.setColor(new Color(0f,0.3f,0.6f,0.8f));
				g.fillRoundRect(x, 0, SquareCraft.calculateStringWidth(g.getFont(), "ID: "+block.getMaterial().getId())+8, height, 3, 3); 
				g.setColor(Color.WHITE);
				g.drawString(block.getMaterial().getDisplayname(), x+4, 20);
				g.setColor(new Color(180,180,180));
				g.setFont(g.getFont().deriveFont(2));
				g.drawString("ID: "+block.getMaterial().getId(), x+4, 35);
			}
		}
	}
	
	private static void updateMouse() {
		mx = (int)(MouseInput.getInstance().mx);
		my = (int)(MouseInput.getInstance().my);
	}
	
}
