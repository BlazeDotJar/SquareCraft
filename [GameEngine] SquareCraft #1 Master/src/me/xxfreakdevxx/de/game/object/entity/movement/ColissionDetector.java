package me.xxfreakdevxx.de.game.object.entity.movement;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.object.entity.Entity;

public class ColissionDetector {
	
	public Entity target = null;
	public SquareCraft scraft = null;
	
	public LeftColission l_col = new LeftColission();
	public RightColission r_col = new RightColission();
	public TopColission t_col = new TopColission();
	public BottumColission b_col = new BottumColission();
	public CornerColission c_col = new CornerColission();
	public MidleColission m_col = new MidleColission();
	
	public ColissionDetector(Entity target) {
		this.target = target;
		this.scraft = SquareCraft.getInstance();
	}
	
	private int x = 0;
	private int y = 0;
	private Location test_loc = null;
	private Location test_loc2 = null;
	public boolean isCollidingButtom() {
		if(target.getWorld() == null) {
			SquareCraft.log("Colission", "Target's world == null");
		}else {
			//b1
			x = target.getUnclonedLocation().getIntX(false) + (target.width / 3);
			y = target.getUnclonedLocation().getIntY(false) + target.height + 1;
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) b_col.b1 = true;
			else b_col.b1 = false;
			//b2
			x = target.getUnclonedLocation().getIntX(false) + (target.width / 3) * 2;
			y = target.getUnclonedLocation().getIntY(false) + target.height + 1;
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) b_col.b2 = true;
			else b_col.b2 = false;
		}
		
		if(b_col.b1  == true|| b_col.b2 == true) target.setOnGround(true);
		else target.setOnGround(false);
		return false;
	}
	
	public boolean isCollidingLeft() {
		if(target.getWorld() == null) {
			SquareCraft.log("Colission", "Target's world == null");
		}else {
			//l1
			x = target.getUnclonedLocation().getIntX(false) - 1;
			y = target.getUnclonedLocation().getIntY(false) + (target.height / 4);
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) l_col.l1 = true;
			else l_col.l1 = false;
			//l2
			x = target.getUnclonedLocation().getIntX(false) - 1;
			y = target.getUnclonedLocation().getIntY(false) + (target.height / 4) * 2;
			test_loc2 = new Location(x,y);
			if(test_loc2.getLocationString().equals(test_loc.getLocationString()) == false) {				
				test_loc = new Location(x,y);
				test_loc.fixLocationToRaster();
				if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) l_col.l2 = true;
				else l_col.l2 = false;
			}else l_col.l2 = l_col.l1;
			//l3
			x = target.getUnclonedLocation().getIntX(false) - 1;
			y = target.getUnclonedLocation().getIntY(false) + (target.height / 4) * 3;			
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) l_col.l3 = true;
			else l_col.l3 = false;
		}
		return false;
	}

	public boolean isCollidingRight() {
		if(target.getWorld() == null) {
			SquareCraft.log("Colission", "Target's world == null");
		}else {
			//r1
			x = target.getUnclonedLocation().getIntX(false) + target.width + 1;
			y = target.getUnclonedLocation().getIntY(false) + (target.height / 4);
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) r_col.r1 = true;
			else r_col.r1 = false;
			//r2
			x = target.getUnclonedLocation().getIntX(false) + target.width + 1;
			y = target.getUnclonedLocation().getIntY(false) + (target.height / 4) * 2;
			test_loc2 = new Location(x,y);
			if(test_loc2.getLocationString().equals(test_loc.getLocationString()) == false) {				
				test_loc = new Location(x,y);
				test_loc.fixLocationToRaster();
				if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) r_col.r2 = true;
				else r_col.r2 = false;
			}else r_col.r2 = r_col.r1;
			//r3
			x = target.getUnclonedLocation().getIntX(false) + target.width + 1;
			y = target.getUnclonedLocation().getIntY(false) + (target.height / 4) * 3;			
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) r_col.r3 = true;
			else r_col.r3 = false;
		}
		return false;
	}
	
	public boolean isCollidingTop() {
		if(target.getWorld() == null) {
			SquareCraft.log("Colission", "Target's world == null");
		}else {
			//t1
			x = target.getUnclonedLocation().getIntX(false) + (target.width / 3) ;
			y = target.getUnclonedLocation().getIntY(false) - 1;
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) t_col.t1 = true;
			else t_col.t1 = false;
			//t2
			x = target.getUnclonedLocation().getIntX(false) + (target.width / 3) * 2;
			y = target.getUnclonedLocation().getIntY(false) - 1;
			test_loc2 = new Location(x,y);
			if(test_loc2.getLocationString().equals(test_loc.getLocationString()) == false) {				
				test_loc = new Location(x,y);
				test_loc.fixLocationToRaster();
				if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) t_col.t2 = true;
				else t_col.t2 = false;
			}else t_col.t2 = t_col.t1;
		}
		return false;
	}
	
	public boolean isCollidingCorners() {
		if(target.getWorld() == null) {
			SquareCraft.log("Colission", "Target's world == null");
		}else {
			//c1
			x = target.getUnclonedLocation().getIntX(false) - 1;
			y = target.getUnclonedLocation().getIntY(false) - 1;
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) c_col.c1 = true;
			else c_col.c1 = false;
			//c2
			x = target.getUnclonedLocation().getIntX(false) + target.width + 1;
			y = target.getUnclonedLocation().getIntY(false) - 1;
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) c_col.c2 = true;
			else c_col.c2 = false;
			//c3
			x = target.getUnclonedLocation().getIntX(false) - 1;
			y = target.getUnclonedLocation().getIntY(false) + target.height + 1;
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) c_col.c3 = true;
			else c_col.c3 = false;
			//c4
			x = target.getUnclonedLocation().getIntX(false) + target.width + 1;
			y = target.getUnclonedLocation().getIntY(false) + target.height + 1;
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) c_col.c4 = true;
			else c_col.c4 = false;
		}
		return false;
	}

	int m_collider_amount = 6;
	public boolean isCollidingMiddle() {
		if(target.getWorld() == null) {
			SquareCraft.log("Colission", "Target's world == null");
		}else {
			//m1
			x = target.getUnclonedLocation().getIntX(false) + (target.width / 2) - 1;
			y = target.getUnclonedLocation().getIntY(false) + (target.height / m_collider_amount) - 1;
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) m_col.m1 = true;
			else m_col.m1 = false;
			//m2
			x = target.getUnclonedLocation().getIntX(false) + (target.width / 2) - 11;
			y = target.getUnclonedLocation().getIntY(false)  + (target.height / m_collider_amount) * 2 - 1;
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) m_col.m2 = true;
			else m_col.m2 = false;
			//m3
			x = target.getUnclonedLocation().getIntX(false) + (target.width / 2) - 1;
			y = target.getUnclonedLocation().getIntY(false)  + (target.height / m_collider_amount) * 3 - 1;
			test_loc = new Location(x,y);
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) m_col.m3 = true;
			else m_col.m3 = false;
			//m4
			x = target.getUnclonedLocation().getIntX(false) + (target.width / 2) - 1;
			y = target.getUnclonedLocation().getIntY(false)  + (target.height / m_collider_amount) * 4 -1;
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) m_col.m4 = true;
			else m_col.m4 = false;
			//m5
			x = target.getUnclonedLocation().getIntX(false) + (target.width / 2) - 1;
			y = target.getUnclonedLocation().getIntY(false)  + target.height + 3;
			test_loc.fixLocationToRaster();
			if(target.getWorld().getBlockAt(test_loc.getLocationString()).getMaterial().isSolid()) m_col.m5 = true;
			else m_col.m5 = false;
		}
		return false;
	}
	
	
	public class LeftColission {
		public boolean l1 = false, l2 = false, l3 = false;
	}
	public class RightColission {
		public boolean r1 = false, r2 = false, r3 = false;		
	}
	public class TopColission {
		public boolean t1 = false, t2 = false;
	}
	public class BottumColission {
		public boolean b1 = false, b2 = false;		
	}
	public class CornerColission {
		public boolean c1 = false, c2 = false, c3 = false, c4 = false;		
	}
	public class MidleColission {
		public boolean m1 = false, m2 = false, m3 = false, m4 = false, m5 = false;	
	}
	
}
