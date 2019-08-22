package me.xxfreakdevxx.de.game.object;

import java.awt.Point;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.environment.World;
import me.xxfreakdevxx.de.game.object.block.Block;
import me.xxfreakdevxx.de.game.object.block.TNTBlock;
import me.xxfreakdevxx.de.game.object.entity.Entity;
import me.xxfreakdevxx.de.game.object.entity.Item;
import me.xxfreakdevxx.de.game.object.entity.movement.Vector;

public class Explosion {
	
	Location location = null;
	int power = 0;
	
	public Explosion(Location loc, int power) {
		this.location = loc;
		this.power = power;
		blast();
	}
	
	public void blast() {
		int size = SquareCraft.randomInteger(power-2, power);
		Location l = location.clone();
		int radius = 0;
		int points = 1;
		double explo_x = l.getX(false);
		double explo_y = l.getY(false);
		for(int ii = 0; ii != size+size+1; ii++) {
			radius+=(SquareCraft.blocksize/2);
			points+=(SquareCraft.blocksize/2);
		    double slice = 2 * Math.PI / points;
		    for (int i = 0; i < points; i++)
		    {
		        double angle = slice * i;
		        int newX = (int)(l.xCurrent+(SquareCraft.blocksize/2) + radius * Math.cos(angle));
		        int newY = (int)(l.yCurrent+(SquareCraft.blocksize/2) + radius * Math.sin(angle));
		        Point p = new Point(newX, newY);
		        Block b = World.getWorld().getBlockAt(new Location(p.getX(),p.getY()).fixLocationToRaster().getLocationString());
		        if(b != null && b.getMaterial() != Material.AIR) {
		        	if(b.getMaterial() == Material.TNT){
		        		((TNTBlock)b).blastDelay = 1;
		        		((TNTBlock)b).interact();
		        	}
		        	b.health -= SquareCraft.randomInteger(6+(size-ii), 8+(size-ii));
//		        	b.health -= SquareCraft.randomInteger(5, 6);
		        	if(b.health <= 0) World.getWorld().breakBlockNaturally(b.getLocation().getLocationString());
		        }
		    }
		}
		for(Entity ent : World.getWorld().getEntities()) {
			if(ent instanceof Item == false)  {
				if(ent.getLocation().getX(false) < explo_x) ent.addVelocity(new Vector(-3, 0));
				else if(ent.getLocation().getX(false) > explo_x) ent.addVelocity(new Vector(3, 0));
				if(ent.getLocation().getY(false) < explo_y) ent.addVelocity(new Vector(0, -5));
				else if(ent.getLocation().getY(false) > explo_y) ent.addVelocity(new Vector(0, 5));
			}
		}
		if(World.getWorld().player.getLocation().getX(false) < explo_x) World.getWorld().player.addVelocity(new Vector(-2, 0));
		else if(World.getWorld().player.getLocation().getX(false) > explo_x) World.getWorld().player.addVelocity(new Vector(2, 0));
		if(World.getWorld().player.getLocation().getY(false) < explo_y) World.getWorld().player.addVelocity(new Vector(0, -2));
		else if(World.getWorld().player.getLocation().getY(false) > explo_y) World.getWorld().player.addVelocity(new Vector(0, 2));
	}
}
