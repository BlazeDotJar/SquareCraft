package me.xxfreakdevxx.de.game.object.entity;

import java.awt.Graphics;

import me.xxfreakdevxx.de.game.Camera;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.gamestate.Playstate;
import me.xxfreakdevxx.de.game.object.ID;
import me.xxfreakdevxx.de.game.object.Material;
import me.xxfreakdevxx.de.game.object.block.Block;

public class Zombie extends Entity {
	
	@SuppressWarnings("unused")
	private Camera cam = SquareCraft.getCamera();
	
	public Zombie(Location location, double health) {
		super(ID.ENEMY, location, SquareCraft.blocksize, SquareCraft.blocksize, health);
		texture = TextureAtlas.getTexture(Material.GRASS.getName());
	}
	
	
	@Override
	public void render(Graphics g) {
		g.drawImage(texture, (int)(getBounds().x), (int)(getBounds().y), getBounds().width, getBounds().height, null);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void damage(double damage, Entity shooter) {
		// TODO Auto-generated method stub
		
	}
	private double gravity = 3d;
	Playstate ps = (Playstate) SquareCraft.getInstance().gsmanager.state;
	@Override
	public void tick() {
		if(world.getBlockAt(getLocation().addAndConvert(0, 1).getLocationString()) != null) {
			Block block = world.getBlockAt(getLocation().addAndConvert(0, 1).getLocationString());
			if(block.getBounds().intersects(this.getBounds())) {
				gravity = 0d;
			}else gravity = 3d;
		}
//		for(Block block : ps.world.blocks.values()) {
//			if(block.getBounds().intersects(this.getBounds())) {
//				gravity = 0d;
//				break;
//			}else gravity = 3d;
//		}
		location.add(0, gravity);
		gravity = 3d;
	}
	
}
