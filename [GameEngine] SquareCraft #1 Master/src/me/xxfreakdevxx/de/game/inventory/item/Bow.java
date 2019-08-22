package me.xxfreakdevxx.de.game.inventory.item;

import java.awt.Point;

import me.xxfreakdevxx.de.game.MouseInput;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.inventory.ItemStack;
import me.xxfreakdevxx.de.game.object.Material;

public class Bow extends ItemStack {

	public Bow() {
		super(Material.BOW);
		this.max_durability = 30;
	}
	
	public void interact() {
		//Auf dem iPad sind Notizen zum Schießen
		int mx = MouseInput.getInstance().mx;
		int my = MouseInput.getInstance().my;
		int px = World.getWorld().player.getLocation().getIntX(false);
		int py = World.getWorld().player.getLocation().getIntY(false);
		int quadrant = getQuadrant(World.getWorld().player.getLocation().getPoint(), new Point(mx, my));
		if(quadrant == 1) {
			
		}else if(quadrant == 2) {
			int a = mx - px;
			int b = my - py;
			double tan = Math.tan(a/b);
			
		}
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
