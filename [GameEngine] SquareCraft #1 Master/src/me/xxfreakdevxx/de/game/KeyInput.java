package me.xxfreakdevxx.de.game;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ConcurrentLinkedQueue;

import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.gamestate.Playstate;
import me.xxfreakdevxx.de.game.inventory.ItemStack;
import me.xxfreakdevxx.de.game.object.Material;
import me.xxfreakdevxx.de.game.object.entity.movement.EntityMovement;

public class KeyInput extends KeyAdapter {
	
	public ConcurrentLinkedQueue<Integer> pressed_keys = new ConcurrentLinkedQueue<Integer>();
	public boolean use_W_TpJump = true;
	
	public KeyInput() {}

	
	private int ticked = 0;
	public void tick() {
		ticked++;
		if(ticked == 1) ticked = 0;
		else return;
		if(World.getWorld() != null && World.getWorld().isGenerated) {			
			for(int key : pressed_keys) {
				switch(key) {
				case KeyEvent.VK_C:
					World.getWorld().player.circular = !World.getWorld().player.circular;
					release(key);
					break;
				case KeyEvent.VK_E:
					if(World.getWorld().player.inventory.showInventory)World.getWorld().player.inventory.close();
					else World.getWorld().player.inventory.open();
					release(key);
					break;
				case KeyEvent.VK_0:
					World.getWorld().player.inventory.addItem(new ItemStack(Material.values()[0]));
					release(key);
					break;
				case KeyEvent.VK_1:
					World.getWorld().player.inventory.addItem(new ItemStack(Material.values()[1]));
					release(key);
					break;
				case KeyEvent.VK_2:
					World.getWorld().player.inventory.addItem(new ItemStack(Material.values()[2]));
					release(key);					
					break;
				case KeyEvent.VK_3:
					World.getWorld().player.inventory.addItem(new ItemStack(Material.values()[3]));
					release(key);					
					break;
				case KeyEvent.VK_4:
					World.getWorld().player.inventory.addItem(new ItemStack(Material.values()[4]));
					release(key);					
					break;
				case KeyEvent.VK_5:
					World.getWorld().player.inventory.addItem(new ItemStack(Material.values()[5]));
					release(key);					
					break;
				case KeyEvent.VK_6:
					World.getWorld().player.inventory.addItem(new ItemStack(Material.values()[6]));
					release(key);					
					break;
				case KeyEvent.VK_7:
					World.getWorld().player.inventory.addItem(new ItemStack(Material.values()[7]));
					release(key);					
					break;
				case KeyEvent.VK_RIGHT:
					SquareCraft.getCamera().addX(1);
					break;
				case KeyEvent.VK_F:
					World.getWorld().physics.allow_gravity = !World.getWorld().physics.allow_gravity;
					if(World.getWorld().physics.allow_gravity) {
						World.getWorld().player.setFallDistance(0d);
						EntityMovement m = World.getWorld().player.getMovement();
						if(m.x_add == -1) m.x_velocity -= 8f;
						else if(m.x_add == 1) m.x_velocity += 8f;
						if(m.y_add == -1) m.y_velocity -= 8f;
						else if(m.y_add == 1) m.y_velocity += 8f;
					}
					release(key);
					break;
				case KeyEvent.VK_N:
					World.getWorld().player.noclip = !World.getWorld().player.noclip;
					break;
				case KeyEvent.VK_M:
					World.getWorld().player.damage(SquareCraft.randomDouble(0, 4));
					break;
				case KeyEvent.VK_G:
					World.getWorld().regenerate();
					break;
				case KeyEvent.VK_LEFT:
					SquareCraft.getCamera().addX(-1);
					break;
				case KeyEvent.VK_UP:
					SquareCraft.getCamera().addY(-1);
					break;
				case KeyEvent.VK_DOWN:
					SquareCraft.getCamera().addY(1);
					break;
				case KeyEvent.VK_L:
					((Playstate)SquareCraft.getInstance().gsmanager.state).world.listBlocks();
					release(key);
					break;
				case KeyEvent.VK_SPACE:
					if(SquareCraft.getInstance().gsmanager.state instanceof Playstate) if(((Playstate)SquareCraft.getInstance().gsmanager.state).world.player.getMovement().pressUp() == false) release(key);
					break;
				case KeyEvent.VK_W:
					if(SquareCraft.getInstance().gsmanager.state instanceof Playstate)
						if(((Playstate)SquareCraft.getInstance().gsmanager.state).world.player.getMovement().pressUp() == false)
							release(key);
					break;
				case KeyEvent.VK_A:
					if(SquareCraft.getInstance().gsmanager.state instanceof Playstate) if(((Playstate)SquareCraft.getInstance().gsmanager.state).world.player.getMovement().pressLeft() == false) release(key);
					break;
				case KeyEvent.VK_S:
					if(SquareCraft.getInstance().gsmanager.state instanceof Playstate) if(((Playstate)SquareCraft.getInstance().gsmanager.state).world.player.getMovement().pressDown() == false) release(key);
					break;
				case KeyEvent.VK_D:
					if(SquareCraft.getInstance().gsmanager.state instanceof Playstate) if(((Playstate)SquareCraft.getInstance().gsmanager.state).world.player.getMovement().pressRight() == false) release(key);
					break;
				}
			}
		}
	}
	int height = 20;
	int space = 2;
	int amount = 1;
	public void render(Graphics g) {
		if(pressed_keys.isEmpty()) {
			g.drawString("Kein Cooldown registriert", 10, 10+space+height);
		}else {
			for(int key : pressed_keys) {
				g.drawString("Key: "+KeyEvent.getKeyText(key), 10, 10+((amount+1)*space)+(amount*height));
				amount++;
			}
		}
		amount=1;
	}
	public void press(int key) {
		if(use_W_TpJump == true && (key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE) && World.getWorld().physics.allow_gravity) {
			if(SquareCraft.getInstance().gsmanager.state instanceof Playstate) ((Playstate)SquareCraft.getInstance().gsmanager.state).world.player.getMovement().jump();
		}else if(pressed_keys.contains(key) == false) {
			pressed_keys.add(key);
		}
	}
	public void release(int key) {
		pressed_keys.remove(key);
		//Spieler Movement zurücksetzen
		if(World.getWorld() != null && World.getWorld().isGenerated) {			
			switch(key) {
			case KeyEvent.VK_SPACE:
				if(SquareCraft.getInstance().gsmanager.state instanceof Playstate) ((Playstate)SquareCraft.getInstance().gsmanager.state).world.player.getMovement().releaseUp();
				break;
			case KeyEvent.VK_W:
				if(SquareCraft.getInstance().gsmanager.state instanceof Playstate) ((Playstate)SquareCraft.getInstance().gsmanager.state).world.player.getMovement().releaseUp();
				break;
			case KeyEvent.VK_A:
				if(SquareCraft.getInstance().gsmanager.state instanceof Playstate) ((Playstate)SquareCraft.getInstance().gsmanager.state).world.player.getMovement().releaseLeft();
				break;
			case KeyEvent.VK_S:
				if(SquareCraft.getInstance().gsmanager.state instanceof Playstate) ((Playstate)SquareCraft.getInstance().gsmanager.state).world.player.getMovement().releaseDown();
				break;
			case KeyEvent.VK_D:
				if(SquareCraft.getInstance().gsmanager.state instanceof Playstate) ((Playstate)SquareCraft.getInstance().gsmanager.state).world.player.getMovement().releaseRight();
				break;
			}
		}
	}
	
	//TODO: Get Interaction
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		press(key);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		release(key);
	}
	
	
}
