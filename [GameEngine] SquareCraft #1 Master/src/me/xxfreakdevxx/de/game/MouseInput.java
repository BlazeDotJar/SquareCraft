package me.xxfreakdevxx.de.game;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ConcurrentLinkedQueue;

import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.environment.World.ChunkManager;
import me.xxfreakdevxx.de.game.gamestate.GSManager.GameState;
import me.xxfreakdevxx.de.game.gamestate.Playstate;
import me.xxfreakdevxx.de.game.object.Material;
import me.xxfreakdevxx.de.game.object.block.Block;
import me.xxfreakdevxx.de.game.object.block.TNTBlock;
import me.xxfreakdevxx.de.game.object.entity.Chicken;
import me.xxfreakdevxx.de.game.object.entity.Pig;
import me.xxfreakdevxx.de.game.object.entity.Zombie;

public class MouseInput extends MouseAdapter {
	
	private Camera camera = SquareCraft.getCamera();
	public int mx = 0;
	public int my = 0;
	public int x_unconverted = 0;
	public int y_unconverted = 0;
	boolean isShiftDown = false;
	boolean isAltDown = false;
	boolean isControlDown = false;
	private ConcurrentLinkedQueue<Integer> pressed = new ConcurrentLinkedQueue<Integer>();
	private static MouseInput instance = null;
	public static MouseInput getInstance() {
		return instance;
	}
	
	public MouseInput() {
		MouseInput.instance = this;
	}
	
	private int clickspeed = 30;
	private int tick = 0;
	public void tick() {
		if(tick < clickspeed) {
			tick++;
		}else {
			for(int key : pressed) {
				switch(key) {
				case MouseEvent.BUTTON1:
					GameState state = null;
					if(SquareCraft.getInstance().gsmanager != null && SquareCraft.getInstance().gsmanager.state != null) state = SquareCraft.getInstance().gsmanager.state;
					if(state instanceof Playstate) {
						Playstate ps = (Playstate)state;
						Location loc = new Location(ps.world, mx, my);
						if(World.world.player.inventory.clicked(key, new Point(x_unconverted, y_unconverted), isShiftDown, isAltDown, isControlDown)) return;
//					if(isOnInventory(p) == false && cursor.getItemStack().getMaterial() == Material.AIR) {
//						temp_loc = new Location(p.getX(), p.getY());
//						temp_loc.fixLocationToRaster();
//						Block b = World.getWorld().getBlockAt(temp_loc.getLocationString());
//						if(b.getMaterial() != Material.AIR) {
//							b.health-=2;
//							if(b.health < 0) b.destroy();
//						}
//					}
						Block block = ps.world.getBlockAt(loc.getLocationString());
						if(block == null || block.getMaterial() == Material.AIR) SquareCraft.log("Mouse", "Block = null");
						else {
							block.health -= World.getWorld().player.hand_block_damage;
							if(block.health < 0) {								
								if(ps.world.breakBlockNaturally(loc.getLocationString()))
									ChunkManager.getChunk((int)(block.getLocation().getIntX(false) / ChunkManager.chunksizePixels)).removeBlock(loc.getLocationString());
							}
						}
					}
					break;
				}
			}			
			tick = 0;
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mx = (int) (e.getX() + camera.getX());
		my = (int) (e.getY() + camera.getY());
		mx = Location.fixToRaster(mx);
		my = Location.fixToRaster(my);
		x_unconverted = e.getX();
		y_unconverted = e.getY();
		if(pressed.contains(e.getButton()) == false) pressed.add(e.getButton());
		super.mouseMoved(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		pressed.remove(e.getButton());
		super.mouseReleased(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		isShiftDown = e.isShiftDown();
		isAltDown = e.isAltDown();
		isControlDown = e.isControlDown();
		mx = (int) (e.getX() + camera.getX());
		my = (int) (e.getY() + camera.getY());
		mx = Location.fixToRaster(mx);
		my = Location.fixToRaster(my);
		x_unconverted = e.getX();
		y_unconverted = e.getY();
		super.mouseDragged(e);
	}
	
	
	public void mousePressed(MouseEvent e) {
		isShiftDown = e.isShiftDown();
		isAltDown = e.isAltDown();
		isControlDown = e.isControlDown();
		pressed.add(e.getButton());
		mx = (int) (e.getX() + camera.getX());
		my = (int) (e.getY() + camera.getY());
		mx = Location.fixToRaster(mx);
		my = Location.fixToRaster(my);
		GameState state = null;
		if(World.getWorld().player != null && World.world.player.inventory.clicked(e.getButton(), e.getPoint(), e.isShiftDown(), e.isAltDown(), e.isControlDown())) return;
		if(SquareCraft.getInstance().gsmanager != null && SquareCraft.getInstance().gsmanager.state != null) state = SquareCraft.getInstance().gsmanager.state;
		if(state instanceof Playstate) {
			Playstate ps = (Playstate)state;
			Location loc = new Location(ps.world, mx, my);
			switch(e.getButton()) {
			case MouseEvent.BUTTON1:
				if(e.isControlDown()){
					for(int i = 0; i != 10; i++) World.getWorld().spawnEntity(new Pig(loc.add(i*SquareCraft.blocksize, 0).clone()));
				} else {
					Block block = ps.world.getBlockAt(loc.getLocationString());
					if(block == null) SquareCraft.log("Mouse", "Block = null");
					else {
						block.health -= World.getWorld().player.hand_block_damage;
						if(block.health < 0) {							
							if(ps.world.breakBlockNaturally(loc.getLocationString()))
								ChunkManager.getChunk((int)(block.getLocation().getIntX(false) / ChunkManager.chunksizePixels)).removeBlock(loc.getLocationString());
						}
					}
				}
				break;
			case MouseEvent.BUTTON3:
				Zombie zombie = new Zombie(new Location(ps.world, mx, my), 20d);
				zombie.setWorld(ps.world);
				if(e.isControlDown()) for(int i = 0; i != 10; i++) World.getWorld().spawnEntity(new Chicken(loc.clone()));
				else if(e.isAltDown()){
					Block block = new TNTBlock(new Location(ps.world,mx,my));
					if(ps.world.setBlock(block))
						ChunkManager.getChunk((int)(block.getLocation().getIntX(false)/ChunkManager.chunksizePixels)).setBlock(block);
				}else {
					Block b = World.getWorld().getBlockAt(loc.getLocationString());
					if(b.getMaterial() == Material.TNT) {
						b.interact();
					}
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
