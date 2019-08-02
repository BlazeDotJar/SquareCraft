package me.xxfreakdevxx.de.game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.xxfreakdevxx.de.game.gamestate.GSManager.GameState;
import me.xxfreakdevxx.de.game.gamestate.Playstate;
import me.xxfreakdevxx.de.game.object.block.Block;
import me.xxfreakdevxx.de.game.object.block.StoneBlock;
import me.xxfreakdevxx.de.game.object.entity.Zombie;

public class MouseInput extends MouseAdapter {
	
	public MouseInput() {
	}
	
	public void mousePressed(MouseEvent e) {
		Camera camera = SquareCraft.getInstance().getCamera();
		int mx = (int) (e.getX() + camera.getX());
		int my = (int) (e.getY() + camera.getY());
		mx = Location.fixToRaster(mx);
		my = Location.fixToRaster(my);
		GameState state = SquareCraft.getInstance().gsmanager.state;
		if(state instanceof Playstate) {
			Playstate ps = (Playstate)state;
			Location loc = new Location(ps.world, mx, my);
			switch(e.getButton()) {
			case MouseEvent.BUTTON1:
				if(e.isShiftDown()) {
					ps.world.getBlockAt(loc.getLocationString()).select();
				}else {					
					Block block = ps.world.getBlockAt(loc.getLocationString());
					if(block == null) SquareCraft.log("Mouse", "Block = null");
					else ps.world.removeBlock(loc.getLocationString());
				}
				break;
			case MouseEvent.BUTTON3:
				Zombie zombie = new Zombie(new Location(ps.world, mx, my), 20d);
				zombie.setWorld(ps.world);
				if(e.isControlDown()) ps.world.zombies.add(zombie);
				else ps.world.setBlock(new StoneBlock(new Location(ps.world, mx, my)));
				break;
			case MouseEvent.BUTTON2:
				if(e.isControlDown()) {
					Block block = ps.world.getBlockAt(loc.getLocationString());
					if(block != null) {
						SquareCraft.log("Block Info", "X/Y: "+block.getLocation().getX(false)+"/"+block.getLocation().getY(false));
					}else SquareCraft.log("Block Info", "Block == null");
				}
				break;
			}
		}
	}
}
