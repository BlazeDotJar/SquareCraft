package me.xxfreakdevxx.de.game.gui.texture;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Texture {
	
	private BufferedImage texture = null;
	
	/* Graphics, fürs Spiegeln von BufferedImages */
	public AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
	public AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	
	public void NONAME() {
		
		tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-texture.getWidth(null), 0);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);			 
		texture = op.filter(texture, null);
	}
	
}
