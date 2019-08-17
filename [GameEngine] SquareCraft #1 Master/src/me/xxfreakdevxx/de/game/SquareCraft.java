package me.xxfreakdevxx.de.game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import me.xxfreakdevxx.de.game.gamestate.GSManager;
import me.xxfreakdevxx.de.game.gamestate.Playstate;

@SuppressWarnings("serial")
public class SquareCraft extends Canvas implements Runnable {
	
	/* Window */
	public static int windowWidth = 1000;//1000 Default
	public static int windowHeight = 700;//700 Default
	
	private boolean isRunning = false;
	public static int fps_current = 0;
	public static double tickSpeed = 120;
	private Thread thread;
	private Camera camera;
	private TextureAtlas textureAtlas;
	public static final int blocksize = 20;
	public static Random ran = new Random();
	
	//Manager, Handler, etc.
	public KeyInput keyinput = null;
	public GSManager gsmanager = null;
	public Window window = null;
	
	static SquareCraft instance;
	public static SquareCraft getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		new SquareCraft();
	}
	
	public SquareCraft() {
		instance = this;
		setBackground(Color.BLACK);
		window = new Window(windowWidth, windowHeight, "SquareCraft", this);
		start();
	}
	
	public void preInit() {
		keyinput = new KeyInput();
		camera = new Camera(0,0);
		new MouseInput();
		this.addKeyListener(keyinput);
		this.addMouseListener(MouseInput.getInstance());
		this.addMouseMotionListener(MouseInput.getInstance());
		textureAtlas = new TextureAtlas();
		gsmanager = new GSManager();
	}
	public void init() {
		readyToRender = true; //Starte die Render-Funktion		
	}
	public void postInit() {
		gsmanager.setState(new Playstate());
	}
	
	private void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
		preInit();
		init();
		postInit();		
	}
	private void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	
	@Override
	public void run() {
		/*
		 * GameLoop - Made by Notch
		 */
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = tickSpeed;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		 long renderLastTime = System.nanoTime();
		 double fps_maximal = 60.0;//MAX FPS
		 boolean infiniteFps = true;
		 double renderNs = 1000000000/fps_maximal;
		 double renderDelta = 0;
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				//update++;
				delta--;
			}
			
			if(infiniteFps) {
				render();
				frames++;
			}else {
				now = System.nanoTime();
				renderDelta += (now - renderLastTime) / renderNs;
				renderLastTime = now;
				while(isRunning && renderDelta >= 1){
					render();
					frames++;
					renderDelta--;
				}				
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps_current = frames;
				frames = 0;
			}
		}
		stop();
	}
	
	public void setWindowTitle(String title) {
		if(window != null) window.frame.setTitle(title);
	}
	public void tick() {
		SquareCraft.windowWidth = window.frame.getWidth();
		SquareCraft.windowHeight = window.frame.getHeight();
		if(readyToRender) gsmanager.tick();
		if(keyinput != null) keyinput.tick();
	}
	private boolean readyToRender = false;
	public void render() {
		if(readyToRender == false) return;
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.GRAY);
		g.clearRect(0, 0, SquareCraft.windowWidth, SquareCraft.windowHeight);
		g.fillRect(0, 0, SquareCraft.windowWidth, SquareCraft.windowHeight);
		gsmanager.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public TextureAtlas getTextureAtlas() {
		return textureAtlas;
	}
	
	public static Camera getCamera() {
		return getInstance().camera;
	}
	public static int calculateStringWidth(Font font, String enteredText) {
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		FontMetrics fm = img.getGraphics().getFontMetrics(font);
		int width = fm.stringWidth(enteredText);
		return width;
	}
	@SuppressWarnings("deprecation")
	public static String getTimeInString() {
		SimpleDateFormat sdf = new SimpleDateFormat();
		Calendar date = sdf.getCalendar();
		Date d = date.getTime();
		return d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
	}
	@SuppressWarnings("deprecation")
	public static String getDateInString() {
		SimpleDateFormat sdf = new SimpleDateFormat();
		Calendar date = sdf.getCalendar();
		Date d = date.getTime();
		int day = d.getDate();
		int mon = d.getMonth();
		int year = d.getYear();
		return day+":"+mon+":"+year;
	}
	public static void log(String prefix, String... strings) {
		if(prefix.equals("")) prefix = "Debug";
		for(String s : strings) {
			System.out.println("["+SquareCraft.getTimeInString()+"]["+prefix+"] "+s);
		}
	}
	public static boolean isLocInOnScrren(double x, double y) {
		if(x >= SquareCraft.getCamera().getX(false) && x <= SquareCraft.getCamera().getX(false)+SquareCraft.windowWidth) {
			if(y >= SquareCraft.getCamera().getY(false) && y <= SquareCraft.getCamera().getY(false)+SquareCraft.windowHeight) {
				return true;
			}
		}
		return false;
	}
	public static boolean isLocInOnScrren(int x, int y) {
		if(x >= SquareCraft.getCamera().getX(false) && x <= SquareCraft.getCamera().getX(false)+SquareCraft.windowWidth) {
			if(y >= SquareCraft.getCamera().getY(false) && y <= SquareCraft.getCamera().getY(false)+SquareCraft.windowHeight) {
				return true;
			}
		}
		return false;
	}
	public static double randomDouble(double min, double max) {
	    if (min >= max) {
	        throw new IllegalArgumentException("max must be greater than min");
	    }
	    return min + (max - min) * ran.nextDouble();
	}
	public static int randomInteger(int min, int max) {
	    if (min >= max) {
	        throw new IllegalArgumentException("max must be greater than min");
	    }
	    return ran.nextInt((max - min) + 1) + min;
	}
}
