package me.xxfreakdevxx.de.game.gamestate;

import java.awt.Graphics;

import me.xxfreakdevxx.de.game.SquareCraft;

public class GSManager {
	
	/*
	 * GameStateManager Klasse
	 */
	public GameState state = null;
	
	public GSManager() {}
	
	public void setState(GameState state) {
		this.state=state;
	}
	public void render(Graphics g) {
		if(state != null) state.render(g);
	}
	public void tick() {
		if(state != null) state.tick();
	}
	
	public enum States {
		
		UNDEFINED,
		STARTING_GAME,
		MAIN_MENU,
		PAUSE_MENU,
		IN_GAME;
		
	}
	public static abstract class GameState {
		
		protected SquareCraft game = null;
		protected States type = States.UNDEFINED;
		
		public GameState() {
			this.game = SquareCraft.getInstance();
		}
		
		public abstract void render(Graphics g);
		public abstract void tick();
		
	}
	
}
