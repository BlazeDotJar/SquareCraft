package me.xxfreakdevxx.de.game.gamestate;

import java.awt.Color;
import java.awt.Graphics;

import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.environment.World.WorldSize;
import me.xxfreakdevxx.de.game.gamestate.GSManager.GameState;
import me.xxfreakdevxx.de.game.gamestate.GSManager.States;

public class Playstate extends GameState {
	
	public World world = null;
	public States type = States.IN_GAME;
	
	public Playstate() {
		SquareCraft.log("Playstate", "setting up Playstate...");
		preInit();
		init();
		postInit();
	}
	public void preInit() {
		world = new World(WorldSize.SMALL);
	}
	public void init() {
		readyToRender = true;
	}
	public void postInit() {
		world.generate();
		world.spawnPlayer();
		if(world.isGenerated) SquareCraft.log("Playstate", "world generated!");
	}
	private boolean readyToRender = false;
	@Override
	public void render(Graphics g) {
		if(readyToRender == false) return;
		if(world != null) world.render(g);
//		g.setColor(Color.RED);
//		g.drawRect((int)(-SquareCraft.getInstance().getCamera().getX()), (int)(-SquareCraft.getInstance().getCamera().getY()), SquareCraft.windowWidth, SquareCraft.windowHeight);
		g.setColor(Color.BLACK);
		g.drawString("FPS: "+SquareCraft.fps_current+" Camera X/Y: "+game.getCamera().getX()+"/"+game.getCamera().getY(), 10, 10);
		g.drawString("Status WORLD: "+world.status, 10, 40);
	}
	@Override
	public void tick() {
		if(readyToRender == false) return;
		if(world != null) world.tick();
	}
	
}
