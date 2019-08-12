package me.xxfreakdevxx.de.game.texture;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.SquareCraft;

public class GameTexture {
	
	private BufferedImage image = null; /* Die Quell-Bilddatei woraus die einzelnen Frames rausgeschnitten werden */
//	private BufferedImage[] frames = null; /* Beinhaltet jedes einzelnes Animation Bild */ 
	private HashMap<Integer, ArrayList<BufferedImage>> frames = new HashMap<Integer, ArrayList<BufferedImage>>();
	private ImageMeta meta = null;
//	private ArrayList<Integer> meta = null;
	private int frame_amount = 0; /* Die Anzahl an Animations Bildern */
	private int current_frame = 0; /* Die aktuelle ID des Bildes, welches als Letztes zurückgegeben wurde */
	public int current_row = 0;
	private double width = 0; /* Die Breite der Frames in Pixel */
	private double height = 0; /* Die Höhe der Frames in Pixel */
	public double fps = 12.0; /* Die Anzahl an Bildern pro Sekunde, wenn die Animation automatisch gerendert werden soll */
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
	public GameTexture(BufferedImage image, String meta_path, int frame_amount, int width, int height, Location loc) {
		super();
		this.image = image;
//		this.meta = meta;
		this.meta = new ImageMeta(image, meta_path);
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
	private boolean cutOutFrames() {
		ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();
		
		for(int y = 0; y != meta.getAmountOfRows(); y++) {
			imgs = new ArrayList<BufferedImage>();
			for(int x = 0; x != meta.getImageAmountInARow(y); x++) {
				imgs.add(image.getSubimage((int)(x*width), (int)(y*height), (int)(width), (int)(height)));
			}
			frames.put(y, imgs);
		}
		return true;
	}

	
	/**
	 * Speichert die Aktuelle Zeile der Frames
	 * 
	 * @param row
	 * @return
	 */
	public GameTexture setRow(int row) {
		this.current_row = row;
		this.current_frame = 0;
		return this;
	}
	
	 
	/**
	 * 	 * nextFrame();
	 * 
	 * Beschreibung:
	 *  - gibt das nächste Bild zurück und beachtet dabei,
	 *    ob die Animation rückwärts oder vorwärts abgespielt werden soll
	 *    
	 * @param timed
	 * @return
	 */
	private long renderLastTime = System.nanoTime();
	private double fps_maximal = fps;//MAX FPS
	private double renderNs = Integer.MAX_VALUE/fps_maximal;
	private double renderDelta = 0;
	public BufferedImage getNextFrame(boolean timed) {
		if(timed == false) {
			
			if(playBackwards) {
				current_frame--;
			    if(current_frame < 0) current_frame = meta.getImageAmountInARow(current_row);
			}else if(playBackwards == false) {
				current_frame++;
				if(current_frame >= meta.getImageAmountInARow(current_row)) current_frame = 0;
			}
			return frames.get(current_row).get(current_frame);
		}else {
			long now = System.nanoTime();
			now = System.nanoTime();
			renderDelta += (now - renderLastTime) / renderNs;
			renderLastTime = now;
			while(renderDelta >= 1){

				renderDelta--;
				if(playBackwards) {
					current_frame--;
				    if(current_frame < 0) current_frame = meta.getImageAmountInARow(current_row);
				}else if(playBackwards == false) {
					current_frame++;
					if(current_frame >= meta.getImageAmountInARow(current_row)) current_frame = 0;
				}
			}
			return frames.get(current_row).get(current_frame);
		}
	}
	
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
	
	public static class ImageMeta  {
		
		public BufferedImage image = null;
		public HashMap<Integer, Integer> meta = null;
		
		public ImageMeta(BufferedImage image, String meta_path) {
			this.image = image;
			MetaLoader.loadMeta(this, meta_path);
		}
		public ImageMeta(BufferedImage image, HashMap<Integer, Integer> meta) {
			this.image = image;
			this.meta = meta;
		}
		
		public int getImageAmountInARow(int row) {
			return meta.get(row);
		}
		
		public void setMeta(HashMap<Integer, Integer> data) {
			this.meta = data;
		}
		
		public int getAmountOfRows() {
			return meta.size();
		}
		
	}
	
	public static class MetaLoader {
		
		static Scanner scanner;
		
		public static void loadMeta(ImageMeta meta, String meta_path) {
			
			HashMap<Integer, Integer> data = new HashMap<Integer, Integer>();
//			scanner = new Scanner(TextureAtlas.class.getResource(meta_path).getFile().get);
			
			try (InputStream is = SquareCraft.class.getResourceAsStream(meta_path)) {
				if (null == is) {
					
				}
				try (BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
					String line = in.readLine();
					String[] s = line.split("");
					String a = s[0];
					String b = s[1];
					while(line != null) {
						s = line.split(":");
						if(s.length == 2) {
							a = s[0];
							a = a.replace(" ", "");
							b = s[1];
							b = b.replace(" ", "");
							data.put(Integer.parseInt(a), Integer.parseInt(b));
							line = in.readLine();
						}
					}
					in.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			meta.setMeta(data);
			
		}
		
	}
	
}
