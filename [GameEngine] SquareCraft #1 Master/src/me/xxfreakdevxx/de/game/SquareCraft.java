package me.xxfreakdevxx.de.game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.xxfreakdevxx.de.game.gamestate.GSManager;
import me.xxfreakdevxx.de.game.gamestate.Playstate;

@SuppressWarnings("serial")
public class SquareCraft extends Canvas implements Runnable {
	
	/* Window */
	public static int windowWidth = 1000;
	public static int windowHeight = 700;
	
	private boolean isRunning = false;
	public static int fps_current = 0;
	public static double tickSpeed = 120;
	private Thread thread;
	private Camera camera;
	private TextureAtlas textureAtlas;
	public static final int blocksize = 32;
	
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
		this.addKeyListener(keyinput);
		this.addMouseListener(new MouseInput());
		camera = new Camera(0,0);
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
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				//update++;
				delta--;
			}
			
			render();
			frames++;
			
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
	
	public Camera getCamera() {
		return camera;
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
}
