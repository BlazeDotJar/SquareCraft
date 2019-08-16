package me.xxfreakdevxx.de.game.object.entity.movement;

import java.util.concurrent.ConcurrentLinkedDeque;

import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.object.block.Block;
import me.xxfreakdevxx.de.game.object.entity.Chicken;
import me.xxfreakdevxx.de.game.object.entity.Entity;
import me.xxfreakdevxx.de.game.object.entity.Pig;
import me.xxfreakdevxx.de.game.object.entity.Player;

public class EntityMovement {
	
	private Entity target = null;
	public boolean isPlayer = false;
	
	private boolean allow_right = true;
	private boolean allow_left = true;
	private boolean allow_up = true;
	private boolean allow_fall = true;
	public float y_velocity = 0f;
	public float x_velocity = 0f;
	
	public boolean isWalkingLeft = false;
	public boolean isIdling = false;
	
	public int move_type = 1;
	
	public ConcurrentLinkedDeque<String> pressed = new ConcurrentLinkedDeque<String>();
	
	private double movement_speed = 3.5d;
	public double fly_movement_speed = 9d;
	public boolean isJumping = false; //Ist der Spieler gerade in einem Sprung?
	private float jumpStrength = 4f; //Sprungkraft
	
	
	public EntityMovement(Entity target) {
		this.target = target;
	}
	
	public boolean pressRight() {
		if(move_type == 1) {
			if(pressed.contains("r") == false) pressed.add("r");
		}
		return true;
	}
	public boolean pressLeft() {
		if(move_type == 1) {
			if(pressed.contains("l") == false) pressed.add("l");
		}
		return true;
	}
	public boolean pressUp() {
		if(move_type == 1) {
			if(pressed.contains("u") == false) pressed.add("u");			
		}
		return true;
	}
	public boolean pressDown() {
		if(move_type == 1) {
			if(pressed.contains("d") == false) pressed.add("d");			
		}
		return true;
	}

	public void releaseRight() {
		pressed.remove("r");
		if(pressed.contains("l") == false) smoothness = 0.0d;
	}
	public void releaseLeft() {
        pressed.remove("l");
        if(pressed.contains("r") == false) smoothness = 0.0d;
	}
	public void releaseUp() {
		pressed.remove("u");
	}
	public void releaseDown() {
		pressed.remove("d");
	}
	
	public int x_add = 0;
	public int y_add = 0;
	private double smoothness = 0.0f;
	public void move() {
		if(pressed.isEmpty() == false) {
			double falldistance = target.getFallDistance() ;
			if(target.isOnGround() == false) target.setFallDistance(falldistance += 0.1);
			else target.setFallDistance(0D);
			ColissionDetector col = target.getColission();
			if(move_type == 1) {
				for(String dir : pressed) {
					if(target instanceof Player == false) {
						if(dir.equals("l") && allow_left && (col.l_col.l1 == true || col.l_col.l2 == true || col.l_col.l3 == true)) jump();
						if(dir.equals("r") && allow_right && (col.r_col.r1 == true || col.r_col.r2 == true || col.r_col.r3 == true)) jump();
					}
					
					if(dir.equals("u") && allow_up && (col.t_col.t1 == false && col.t_col.t2 == false && (col.c_col.c1 == false || col.c_col.c2 == false))) y_add = -1;
					if(dir.equals("d") && allow_fall && (col.b_col.b1 == false && col.b_col.b2 == false && (col.c_col.c3 == false || col.c_col.c4 == false) && isJumping == false )) y_add = 1;
					
					if(dir.equals("l") && allow_left && (col.l_col.l1 == false && col.l_col.l2 == false && col.l_col.l3 == false && (col.c_col.c1 == false || col.c_col.c3 == false))) x_add = -1;
					if(isJumping == false){
//						 jump();
					}
					if(dir.equals("r") && allow_right && (col.r_col.r1 == false && col.r_col.r2 == false && col.r_col.r3 == false && (col.c_col.c2 == false || col.c_col.c4 == false))) x_add = 1;
					if(isJumping == false){
//						 jump();
					}
				}
			}
			if(x_add == 1) { isWalkingLeft = false; isIdling = false; }
			else if(x_add == -1) { isWalkingLeft = true; isIdling = false; }
			
			if(x_add == -1 || x_add == 1) if(smoothness < 0.5D) smoothness += 0.02D;
			if(World.getWorld().physics.allow_gravity) target.getUnclonedLocation().add((x_add * movement_speed * smoothness), (y_add * movement_speed));
			else target.getUnclonedLocation().add((x_add * fly_movement_speed * smoothness), (y_add * fly_movement_speed));
		}
		target.getUnclonedLocation().add(x_velocity, y_velocity);
		if(x_velocity > 0f) x_velocity -= 0.1f;
		else if(x_velocity < 0f) x_velocity += 0.1f;
		if(y_velocity > 0f) y_velocity -= 0.1f;
		else if(y_velocity < 0f) y_velocity += 0.1f;
		if(target.getColission().m_col.m5 == true && target.getColission().m_col.m4 == false) target.getUnclonedLocation().setFixYtoRaster();
		applyJump();
		
		if(x_add == 0 && y_add == 0 && isJumping == false && y_velocity == 0 && x_velocity == 0) isIdling = true;
		
		if(target.getGameTexture() != null) setGameTextureData();
		x_add=0;
		y_add=0;
	}
	
	public void setGameTextureData() {
		if(target instanceof Player) {
			if(isWalkingLeft == true && isIdling == false && target.getGameTexture().current_row != 3) target.getGameTexture().setRow(3);
			if(isWalkingLeft == false && isIdling == false && target.getGameTexture().current_row != 2) target.getGameTexture().setRow(2);
			
			if(isWalkingLeft == true && isIdling == true && target.getGameTexture().current_row != 1) target.getGameTexture().setRow(1);
			if(isWalkingLeft == false && isIdling == true && target.getGameTexture().current_row != 0) target.getGameTexture().setRow(0);
			
			if(isWalkingLeft == false && isIdling == false && isJumping == true && target.getGameTexture().current_row != 4) target.getGameTexture().setRow(4);
			else if(isWalkingLeft == true && isIdling == false && isJumping == true && target.getGameTexture().current_row != 5) target.getGameTexture().setRow(5);
		}else if(target instanceof Pig || target instanceof Chicken) {
			if(isWalkingLeft == false && isIdling == false && target.getGameTexture().current_row != 2) target.getGameTexture().setRow(2);
			if(isWalkingLeft == true && isIdling == false && target.getGameTexture().current_row != 3) target.getGameTexture().setRow(3);
			
			if(isWalkingLeft == true && isIdling == true && target.getGameTexture().current_row != 1) target.getGameTexture().setRow(1);
			if(isWalkingLeft == false && isIdling == true && target.getGameTexture().current_row != 0) target.getGameTexture().setRow(0);
		}
	}
	
	private float cur_jump_strength = 0f; 
	private void applyJump() {
		if(isJumping) {
			if(target.getColission().t_col.t1 == true || target.getColission().t_col.t2 == true) {
				resetJumpSettings();
				return;
			}
			if(cur_jump_strength != jumpStrength) {
				target.setFallDistance(0d);
				if(target instanceof Pig) {
					cur_jump_strength += 0.2f;
				}else cur_jump_strength += 0.1f;
				target.getUnclonedLocation().add(0d, (double)-(jumpStrength - cur_jump_strength));
			}else resetJumpSettings();
			if(target.isOnGround() == true && cur_jump_strength >= 0.6f) resetJumpSettings();
		}
	}
	public void jump() {
		if(target.isOnGround() && isJumping == false) {
			isJumping = true;
		}
	}
	private void resetJumpSettings() {
		cur_jump_strength = 0f;
		y_velocity = 0f;
		isJumping = false;
		target.getColission().isCollidingButtom();
	}
	
	public void addGravity(double gravity) {
		if(allow_fall && target.isOnGround() == false) {
			target.getUnclonedLocation().add(0d, gravity);
			target.getColission().isCollidingButtom();
		}
	}
	
	public Block getNextBlock(int dir) {
		int x = target.getLocation().getIntX(false);
		int y = target.getLocation().getIntY(false);
		return World.getWorld().getBlockAt(x+(dir*SquareCraft.blocksize), y);
	}
	
}
