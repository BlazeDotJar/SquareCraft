package me.xxfreakdevxx.de.game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.xxfreakdevxx.de.game.environment.World.ChunkManager;
import me.xxfreakdevxx.de.game.gamestate.GSManager.GameState;
import me.xxfreakdevxx.de.game.gamestate.Playstate;
import me.xxfreakdevxx.de.game.object.block.Block;
import me.xxfreakdevxx.de.game.object.block.SandBlock;
import me.xxfreakdevxx.de.game.object.entity.Zombie;

public class MouseInput extends MouseAdapter {
	
	private Camera camera = SquareCraft.getCamera();
	public int mx = 0;
	public int my = 0;
	private static MouseInput instance = null;
	public static MouseInput getInstance() {
		return instance;
	}
	
	public MouseInput() {
		MouseInput.instance = this;
	}
	
	public void mousePressed(MouseEvent e) {
		mx = (int) (e.getX() + camera.getX());
		my = (int) (e.getY() + camera.getY());
		mx = Location.fixToRaster(mx);
		my = Location.fixToRaster(my);
		GameState state = null;
		if(SquareCraft.getInstance().gsmanager.state != null) state = SquareCraft.getInstance().gsmanager.state;
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
					else {
						if(ps.world.removeBlock(loc.getLocationString()))
							ChunkManager.getChunk((int)(block.getLocation().getIntX(false) / ChunkManager.chunksizePixels)).removeBlock(loc.getLocationString());
					}
				}
				break;
			case MouseEvent.BUTTON3:
				Zombie zombie = new Zombie(new Location(ps.world, mx, my), 20d);
				zombie.setWorld(ps.world);
				if(e.isControlDown()) ps.world.zombies.add(zombie);
				else {
					Block block = new SandBlock(new Location(ps.world,mx,my));
					if(ps.world.setBlock(block))
						ChunkManager.getChunk((int)(block.getLocation().getIntX(false)/ChunkManager.chunksizePixels)).setBlock(block);
				}
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