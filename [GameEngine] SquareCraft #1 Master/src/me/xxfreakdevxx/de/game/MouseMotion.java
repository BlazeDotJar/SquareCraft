package me.xxfreakdevxx.de.game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotion implements MouseMotionListener {
	
	@Override
	public void mouseMoved(MouseEvent e) {
		MouseInput.getInstance().mx = (int) (e.getXOnScreen() + SquareCraft.getCamera().getX());
		MouseInput.getInstance().my = (int) (e.getY() + SquareCraft.getCamera().getY());
		MouseInput.getInstance().mx = Location.fixToRaster(MouseInput.getInstance().mx);
		MouseInput.getInstance().my = Location.fixToRaster(MouseInput.getInstance().my);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}
	
}
