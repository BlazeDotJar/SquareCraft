package me.xxfreakdevxx.de.game.inventory.item;

import java.awt.Point;

import me.xxfreakdevxx.de.game.MouseInput;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.inventory.ItemStack;
import me.xxfreakdevxx.de.game.object.Material;
import me.xxfreakdevxx.de.game.object.entity.Pig;
import me.xxfreakdevxx.de.game.object.entity.movement.Vector;

public class Bow extends ItemStack {

	public Bow() {
		super(Material.BOW);
		this.max_durability = 30;
	}
	
	double angle = 0.0d;
	double arc = 0.0d;
	double ank = 0.0d;
	double geg = 0.0d;
	double hyp = 0.0d;
	int mx, my, px, py;
	double targetx = 0.0d;
	double targety = 0.0d;
	int radius = 10;
	double bx = 0d;
	double by = 0d;
	double sx = 0d;
	double sy = 0d;
	double xv = 0d;
	double yv = 0d;
	public void interact() {
//		System.out.println("Shoot!");
		//Auf dem iPad sind Notizen zum Schießen
		/* Deaktiviert aufgrund von  */
//		mx = (int)(MouseInput.getInstance().x_unconverted + SquareCraft.getCamera().getX());
//		my = (int)(MouseInput.getInstance().y_unconverted + SquareCraft.getCamera().getY());
//		px = (int)(World.getWorld().player.getLocation().getIntX(false));
//		py = (int)(World.getWorld().player.getLocation().getIntY(false));
//		
//		int tx = px-mx;
//		int ty = py-my;
//		if(tx > 0) {
//			if(tx > radius) xv = radius;
//			else xv = tx;
//		}else {
//			if(tx < -radius) xv = -radius;
//			else xv = tx;
//		}
//		if(ty > 0) {
//			if(ty > (radius)) yv = radius;
//			else yv = ty;			
//		}else {			
//			if(ty < -(radius)) yv = -radius;
//			else yv = ty;						
//		}
//		//Richtung berichtigen
//		xv *= (-1);
//		yv *= (-1);
//		
//		System.out.println("XV/YV: "+xv+"/"+yv);
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		World.getWorld().player.bows.clear();
//		World.getWorld().player.bows.add(new Point((int)targetx, (int)targety));
//		World.getWorld().player.bows.add(new Point((int)px, (int)py));
//		Pig pig = new Pig(World.getWorld().player.getLocation());
//		pig.addVelocity(new Vector(xv, yv));
//		World.getWorld().spawnEntity(pig);
	}
	
	
	//gibt einen Quadranten zurück, in dem sich der Punkt p befindet
	public int getQuadrant(Point center, Point p) {
		double cx = center.getX();
		double cy = center.getY();
		double x = p.getX();
		double y = p.getY();
		if(x < cx && y < cy) return 1;
		if(x > cx && y < cy) return 2;
		if(x < cx && y > cy) return 3;
		if(x > cx && y > cy) return 4;
		return 0;
	}

}
