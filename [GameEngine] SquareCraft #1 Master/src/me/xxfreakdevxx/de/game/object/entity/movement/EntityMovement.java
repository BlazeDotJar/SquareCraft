package me.xxfreakdevxx.de.game.object.entity.movement;

import java.util.concurrent.ConcurrentLinkedDeque;

import me.xxfreakdevxx.de.game.object.entity.Entity;

public class EntityMovement {
	
	private Entity target = null;
	
	private boolean allow_right = true;
	private boolean allow_left = true;
	private boolean allow_up = true;
	private boolean allow_fall = true;
	
	public int move_type = 1;
	
	private ConcurrentLinkedDeque<String> pressed = new ConcurrentLinkedDeque<String>();
	
	private double movement_speed = 4.5d;
	
	
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
	
	private int x_add = 0;
	private int y_add = 0;
	private double smoothness = 0.0f;
	public void move() {
		if(pressed.isEmpty() == false) {
			ColissionDetector col = target.getColission();
			if(move_type == 1) {
				for(String dir : pressed) {
					if(dir.equals("u") && allow_up && (col.t_col.t1 == false && col.t_col.t2 == false && (col.c_col.c1 == false || col.c_col.c2 == false))) y_add = -1;
					if(dir.equals("d") && allow_fall && (col.b_col.b1 == false && col.b_col.b2 == false && (col.c_col.c3 == false || col.c_col.c4 == false) && isJumping == false )) y_add = 1;
					
					if(dir.equals("l") && allow_left && (col.l_col.l1 == false && col.l_col.l2 == false && col.l_col.l3 == false && (col.c_col.c1 == false || col.c_col.c3 == false))) x_add = -1;
					if(dir.equals("r") && allow_right && (col.r_col.r1 == false && col.r_col.r2 == false && col.r_col.r3 == false && (col.c_col.c2 == false || col.c_col.c4 == false))) x_add = 1;
				}
			}
			
			if(x_add == -1 || x_add == 1) if(smoothness < 0.5D) smoothness += 0.02D;
			target.getUnclonedLocation().add((x_add * movement_speed * smoothness), (y_add * movement_speed));				
			x_add=0;
			y_add=0;
		}
		if(target.getColission().m_col.m5 == true && target.getColission().m_col.m4 == false) target.getUnclonedLocation().setFixYtoRaster();
		applyJump();
	}
	
	
	private boolean isJumping = false;
	private float jumpStrength = 5.7f;
	private float cur_jump_strength = 0f;
	private void applyJump() {
		if(isJumping) {
			if(target.getColission().t_col.t1 == true || target.getColission().t_col.t2 == true) {
				resetJumpSettings();
				return;
			}
			if(cur_jump_strength != jumpStrength) {
				target.setFallDistance(0d);
				cur_jump_strength += 0.2f;
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
		isJumping = false;
		target.getColission().isCollidingButtom();
	}
	
	public void addGravity(double gravity) {
		if(allow_fall && target.isOnGround() == false) target.getUnclonedLocation().add(0d, gravity);
	}
	
}
