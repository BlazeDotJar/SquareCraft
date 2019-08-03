package me.xxfreakdevxx.de.game.environment;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import me.xxfreakdevxx.de.game.Camera;
import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.object.block.AirBlock;
import me.xxfreakdevxx.de.game.object.block.Block;
import me.xxfreakdevxx.de.game.object.block.DirtBlock;
import me.xxfreakdevxx.de.game.object.block.GrassBlock;
import me.xxfreakdevxx.de.game.object.block.StoneBlock;
import me.xxfreakdevxx.de.game.object.entity.Player;
import me.xxfreakdevxx.de.game.object.entity.Zombie;

public class World {
	
	public String worldname = "overworld";
	public String seed = "";
	public WorldSize size = WorldSize.XX_SMALL;
	private ConcurrentHashMap<String, Block> blocks = new ConcurrentHashMap<String, Block>();
	public ConcurrentLinkedQueue<Zombie> zombies = new ConcurrentLinkedQueue<Zombie>();
	public boolean isGenerating = false;
	public boolean isGenerated = false;
	public boolean showRaster = false;
	public Player player = null;
	public Physics physics = null;
	public String status = "";
	public static World world = null;
	
	public WorldGenerator generator = null;
	
	public World(WorldSize size) {
		this.size = size;
		this.physics = new Physics(this);
		this.generator = new WorldGenerator(this);
		World.world = this;
	}
	
	protected int border_distance_x = 0;
	protected int border_distance_y = 15;
	public void generate() {
		isGenerating = true;
		generator.generate();
		SquareCraft.log("World", "Generated Blocks: "+blocks.keySet().toString());
		status = "World is generated";
		isGenerating = false;
		isGenerated = true;
	}
	
	/*
	 * Block Methods
	 */
	public Block getBlockAt(String loc_string) { 
		if(blocks.containsKey(loc_string) == false) blocks.put(loc_string, new AirBlock(new Location(loc_string)));
		return blocks.get(loc_string);
	}
	public boolean setBlock(Block block) {
		if(blocks.containsKey(block.getLocation().getLocationString())) blocks.remove(block.getLocation().getLocationString());
		blocks.put(block.getLocation().getLocationString(), block);
		return true;
	}
	public void removeBlock(String loc_string) {
		if(blocks.containsKey(loc_string)) blocks.remove(loc_string);
	}
	
	private int width_blocks_amount = 0;//Wie viele Blöcke passen in die breite des Bildschirmes?
	private int height_blocks_amount = 0;//Wie viele Blöcke passen in die Höhe des Bildschirmes?
	private int x_r = 0;
	private int y_r = 0;
	private Block render_block = null;
	private Camera cam = SquareCraft.getInstance().getCamera();
	private Location location = new Location(this, 0d, 0d);;
	private int rendered_blocks = 0;
	private int bsize = SquareCraft.blocksize;
	public void render(Graphics g) {
		if(isGenerated == false) return;
		g.drawImage(TextureAtlas.getTexture("welt-bg.jpg"), 0,0, SquareCraft.windowWidth, SquareCraft.windowHeight, null);
		//Es darf keine Camera Coords addiert werden in der Render Methode!
		//Des werden nur die Blöcke gerendert, die im Bildschirm sind
		rendered_blocks = 0;
		width_blocks_amount = (int)(SquareCraft.windowWidth / SquareCraft.blocksize);
		height_blocks_amount = (int)(SquareCraft.windowHeight / SquareCraft.blocksize);
		for(int x = -1; x != width_blocks_amount+1; x++) {
			for(int y = -1; y != height_blocks_amount+1; y++) {
				x_r = (int)(cam.getX()+(x*bsize));
				y_r = (int)(cam.getY()+(y*bsize));
				x_r = Location.fixToRaster(x_r);
				y_r = Location.fixToRaster(y_r);
				location.setXY(x_r, y_r);
				render_block = blocks.get(location.getLocationString());
				
				if(render_block == null) /*System.out.println("Block == null")*/;
				else {
					rendered_blocks++;
					render_block.render(g);
				}
			}
			
		}
		status = "Rendered Blocks: "+rendered_blocks;
		status = status+" LOC:"+location.getLocationString();
		for(Zombie z : zombies) z.render(g);
		if(player != null) player.render(g);
	}
	
	public void tick() {
		for(Block b : blocks.values()) b.tick();
		if(player != null) player.tick();
		for(Zombie z : zombies) z.tick();
		SquareCraft.getInstance().getCamera().tick(player);
		physics.applyGravity();
	}
	public void listBlocks() {
		for(String s : blocks.keySet()) {
			SquareCraft.log("World", "Key: "+s+" Block: "+blocks.get(s).getMaterial().getName());
		}
	}
	
	public void spawnPlayer() {
		if(player == null) {
			player = new Player(new Location(3*SquareCraft.blocksize, 3*SquareCraft.blocksize));
			player.setWorld(this);
		}
	}
	
	public static World getWorld() {
		return world;
	}
	
	public static enum WorldSize {
		
		XXX_SMALL(2,2),
		XX_SMALL(9,9),
		X_SMALL(20,10),
		SMALL(120,40),
		MEDIUM(100,50),
		LARGE(1300,400),
		X_LARGE(2600,400),
		XX_LARGE(3800,400),
		FILL_SCREEN(37, 28),
		EMPTY(0, 0),
		FLAT(40, 10);
		
		//Breite der Welt in Blöcken
		private int worldwidth = 2;
		private int worldheight = 2;
		
		WorldSize(int worldwidth, int worldheight) {
			this.worldwidth = worldwidth;
			this.worldheight = worldheight;
		}
		
		public int getWidth() {
			return worldwidth;
		}
		public int getHeight() {
			return worldheight;
		}	
	}
	
	public class WorldGenerator {
		
		private Program program = null;
		private int x = 0;
		private int y = 0;
		private World world = null;
		
		public WorldGenerator(World world) {
			this.world = world;
			y = world.border_distance_y;
			program = new SmoothProgram(world);
		}
		
		public void generate() {
			((SmoothProgram)program).generate();
			for(int x : program.getPoints().keySet()) buildLine(x, program.getPoints().get(x));
			((SmoothProgram)program).smoothAll();
			System.out.println("points: "+program.getPoints().size());
		}
		public void buildLine(int x, int y) {
			int height = World.getWorld().size.getHeight();
			int dirt_range = 1;
			for(int i = 0; i != height-y; i++) {
				dirt_range = program.randomInteger(10, 15);
				if(i == 0) {
					world.setBlock(new GrassBlock(new Location(x*SquareCraft.blocksize, (y+i)*SquareCraft.blocksize)));
				}else if(i == 1) {
					for(int yi = 0; yi < dirt_range; yi++) {
						if(yi!=0) i++;
						world.setBlock(new DirtBlock(new Location(x*SquareCraft.blocksize, (y+i)*SquareCraft.blocksize)));
					}
				}else world.setBlock(new StoneBlock(new Location(x*SquareCraft.blocksize, (y+i)*SquareCraft.blocksize)));
			}
		}
		
		public class SmoothProgram extends Program {

			public SmoothProgram(World world) {
				super(world);
			}
			
			private int normal_null = (SquareCraft.windowHeight)+1;
			private int r = 0;
			
			@Override
			public void generate() {
				int tick = 0;
				for(int i = 0; i != 200; i++) {
				    if(tick == 50) {
				        r = randomInteger(1, 4);
				        tick = 0;
				    }else{
				        r = randomInteger(5, 10);
				    }
//				    if(y > 0) {
//				    }else System.out.println("A");
				    if(ran.nextBoolean()) {// Chance, dass die Höhe sich verändert
				    	if(ran.nextBoolean()) {
				    		r = smoothY(r);
				    		r *= (-1);
				    		y += r;
				    	}else {
				    		r = smoothY(r);
				    		y+=r;
				    	}
				    	if(y > normal_null) {
				    		y =normal_null-1;
				    	}  
				    }
				    tick++;
				    x++;
				    points.put(x, y);
				}
			}			
			private int y_range = 1;
			public int smoothY(int y) {
				if(y > y_range) y = y_range;
				return y;
			}
			public void smoothAll() {
				for(int x : getPoints().keySet()) {
					int y_0 = getPoints().get(x);
					if((x - 1 <= 0) == false) y_0 = getPoints().get(x-1);
					
					int y_1 = getPoints().get(x);
					
					int y_2 = y_1;
					if(((x+1) >= getPoints().size()) == false) y_2 = getPoints().get(x+1);
					
					if(y_1 > y_0 && y_1 > y_2) y_1 = y_0;
					else if(y_1 < y_0 && y_1 < y_2) y_1 = y_0;
				}
			}
		}
		
		public abstract class Program {
			
			protected HashMap<Integer, Integer> points = new HashMap<Integer, Integer>();
			protected boolean isGenerating = false;
			protected World world = null;
			protected Random ran = new Random();
			
			public Program(World world) {
				this.world = world;
			}
			public abstract void generate();
			
			//Points beinhaltet die Locations, an denen eine Reihe von Blöcken in Richtung Y+ gebildert werden sollen
			protected HashMap<Integer, Integer> getPoints() {
				return points;
			}
			
			protected double randomDouble(double min, double max) {
			    if (min >= max) {
			        throw new IllegalArgumentException("max must be greater than min");
			    }
			    return min + (max - min) * ran.nextDouble();
			}
			protected int randomInteger(int min, int max) {
			    if (min >= max) {
			        throw new IllegalArgumentException("max must be greater than min");
			    }
			    return ran.nextInt((max - min) + 1) + min;
			}
			
		}
		
	}
	
	public static class GeneratorSettings {
		
		public static int min_dirt_range = 3;
		public static int max_dirt_range = 10;
		
	}
	
}
