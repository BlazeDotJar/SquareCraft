package me.xxfreakdevxx.de.game.object.entity.movement.behavior;

import java.util.Random;

import me.xxfreakdevxx.de.game.object.entity.Entity;

public class EntityBehavior {
	
	public Entity target = null;
	public int direction_x = 0;
	public Random ran = new Random();
	public int steps = 0;
	public int idle_time = 0;
	public int maximal_steps = 100;
	public int maximal_idle_time = 2*50;
	public Status status = Status.IDLE;
	
	public EntityBehavior(Entity target) {
		this.target = target;
	}
	
	public void move() {
		if(status == Status.IDLE) {
			if(idle_time == 0) idle_time = randomInteger(0, maximal_idle_time);
			idle_time --;
			if(idle_time == 0) status = Status.WALKING;
			
		}else if(status == Status.WALKING) {
			if(steps != 0) {
				if(direction_x == 1) {
					target.movement.pressRight();
				}else if(direction_x == -1) {
					target.movement.pressLeft();
				}else if(direction_x == 0) {/*EMPTY*/}
				steps--;
				if(steps == 0) {
					status = Status.IDLE;
					idle_time = randomInteger(0, maximal_idle_time);
					target.movement.releaseRight();
					target.movement.releaseLeft();
					direction_x = 0;
				}
			}else {
				if(ran.nextBoolean()) { //Soll die Entität sich auf der X Achse bewegen?
					status = Status.WALKING;
					steps = randomInteger(0, maximal_steps);
					if(ran.nextBoolean()) {
						//LINKS
						target.movement.releaseRight();
						target.movement.pressLeft();
						direction_x = -1;
					}else{
						//RECHTS
						target.movement.releaseLeft();
						target.movement.pressRight();
						direction_x = 1;
					}
				}else {
					status = Status.IDLE;
					idle_time = randomInteger(0, maximal_idle_time);
					target.movement.releaseRight();
					target.movement.releaseLeft();
					direction_x = 0;
				}
			}
		}

	}
	
	public int randomInteger(int min, int max) {
	    if (min >= max) {
	        throw new IllegalArgumentException("max must be greater than min");
	    }
	    return ran.nextInt((max - min) + 1) + min;
	}
	public enum Status {
		IDLE,
		WALKING;
	}
	
}
