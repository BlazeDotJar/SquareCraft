package me.xxfreakdevxx.de.game.texture;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import me.xxfreakdevxx.de.game.Location;

public class GameTexture2 {
	
	private BufferedImage image = null; /* Die Quell-Bilddatei woraus die einzelnen Frames rausgeschnitten werden */
//	private BufferedImage[] frames = null; /* Beinhaltet jedes einzelnes Animation Bild */ 
	private HashMap<Integer, ArrayList<BufferedImage>> frames = new HashMap<Integer, ArrayList<BufferedImage>>();
	private int frame_amount = 0; /* Die Anzahl an Animations Bildern */
	private int current_frame = 0; /* Die aktuelle ID des Bildes, welches als Letztes zurückgegeben wurde */
	public int current_row = 0;
	private int width = 0; /* Die Breite der Frames in Pixel */
	private int height = 0; /* Die Höhe der Frames in Pixel */
	private int fps = 12; /* Die Anzahl an Bildern pro Sekunde, wenn die Animation automatisch gerendert werden soll */
	private Location loc = null; /* Die Position, an der die Animation gezeichnet werden soll. ACHTUNG: Die Location muss UNCLONED sein ! */
	private boolean playBackwards = false; /* Gibt an, ob die Animation rückwärts abgespielt werden soll. false = vorwärts, true = rückwärts */
	
	
	/**
	 * Der Constructor überträgt die Variablen-Werte auf die Fields
	 * 
	 * @param image
	 * @param frame_amount
	 * @param width
	 * @param height
	 * @param loc
	 */
	public GameTexture2(BufferedImage image, int frame_amount, int width, int height, Location loc) {
		super();
		this.image = image;
		this.frame_amount = frame_amount;
		this.width = width;
		this.height = height;
		this.loc = loc;
		cutOutFrames();
	}
	
	/**
	 * 
	 * Schneidet die Frames aus dem Spritesheet heraus und speichert sie im frames Array
	 * 
	 * @return
	 */
//	private boolean cutOutFrames() {
//		frames = new BufferedImage[frame_amount];
//		int id = 0;
//		for(int x = 0; x != (image.getWidth() / width)-1; x++) {
//			for(int y = 0; y != (image.getHeight() / height)-1; y++) {
//				frames[id] = image.getSubimage(x*width, y*height, width, height);
//				if(id >= frame_amount == false)	id++;
//				else break;
//			}
//			if(id >= frame_amount == false) id++;
//			else break;
//		}
//		System.out.println("Es wurden "+id+" Frames geladen");
//		return true;
//	}
	private boolean cutOutFrames() {
		ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();
		System.out.println("Y: "+image.getHeight()/height);
		
		for(int y = 0; y != (image.getHeight()/height); y++) {
			imgs = new ArrayList<BufferedImage>();
			for(int x = 0; x != (image.getWidth()/width); x++) {
				
				imgs.add(image.getSubimage(x*width, y*height, width, height));
			}
			frames.put(y, imgs);
			System.out.println("row: "+y + "  Frames size: "+imgs.size());
		}
		return true;
	}
	
	/**
	 * Gibt den nächsten Frame zurück
	 * 
	 * @param row
	 * @return
	 */
	private long renderLastTime = System.nanoTime();
	private double fps_maximal = 60.0;//MAX FPS
	private double renderNs = 1000000000/fps_maximal;
	private double renderDelta = 0;
	public BufferedImage getNextFrame(boolean timed) {
		if(timed == false) {
//			System.out.println("Requested Row: "+current_row);
			if(frames.get(current_row) == null) {
//				System.out.println("Requested is Null");
				return null;
			}
			
			
			if(playBackwards == true) current_frame--;
			if(playBackwards == false) current_frame++;
			
			if(frames.get(current_row) == null) {
				System.out.println("row: "+current_row+" == null");
			}
//			System.out.println("current_row = "+current_row);
			if(playBackwards && current_row == 0) current_frame = frames.get(current_row).size();
			
			else if(playBackwards == false && current_row == frames.get(current_row).size()-2) current_frame = 0;
			
			if(current_frame >= frames.get(current_row).size()) return null;
			return frames.get(current_row).get(current_frame);

		}else {
			long now = System.nanoTime();
			now = System.nanoTime();
			renderDelta += (now - renderLastTime) / renderNs;
			renderLastTime = now;
			while(renderDelta >= 1){

				renderDelta--;
				return frames.get(current_row).get(current_frame);
			}
			return null;
		}
	}
	
	/**
	 * Speichert die Aktuelle Zeile der Frames
	 * 
	 * @param row
	 * @return
	 */
	public GameTexture2 setRow(int row) {
		this.current_row = row;
		this.current_frame = 0;
		return this;
	}
	
	/**
	 * nextFrame();
	 * 
	 * Beschreibung:
	 *  - gibt das nächste Bild zurück und beachtet dabei,
	 *    ob die Animation rückwärts oder vorwärts abgespielt werden soll
	 *
	 * @return
	 */
	public BufferedImage nextFrame() {
		//Untested
//		if(playBackwards == false) {			
//			if(current_frame == (frame_amount-1)) current_frame = 0;
//			current_frame ++;
//			return frames[current_frame];
//		}else {
//			if(current_frame == 0) current_frame = (frame_amount-1);
//			current_frame --;
//			return frames[current_frame];
//		}
		return null;
	}
	
	/**
	 * Gibt einen Frame zurück
	 * 
	 * @param id
	 * @return
	 */
//	public BufferedImage getFrame(int id) {
//		return frames[id];
//	}
	
	/**
	 * 
	 * Flip ein Image horizontal und vertikal, wenn gefordert, und gibt es zurück
	 * 
	 * @param image
	 * @param horizontal
	 * @param vertical
	 * @return
	 */
	public BufferedImage flip(BufferedImage image, boolean horizontal, boolean vertical) {
		
		
		
		return image;
	}
	
}
