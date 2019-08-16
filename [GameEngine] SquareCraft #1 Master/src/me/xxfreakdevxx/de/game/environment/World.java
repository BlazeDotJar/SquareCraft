package me.xxfreakdevxx.de.game.environment;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import me.xxfreakdevxx.de.game.Camera;
import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.object.Material;
import me.xxfreakdevxx.de.game.object.block.AirBlock;
import me.xxfreakdevxx.de.game.object.block.Block;
import me.xxfreakdevxx.de.game.object.block.DirtBlock;
import me.xxfreakdevxx.de.game.object.block.GrassBlock;
import me.xxfreakdevxx.de.game.object.block.StoneBlock;
import me.xxfreakdevxx.de.game.object.entity.Entity;
import me.xxfreakdevxx.de.game.object.entity.Item;
import me.xxfreakdevxx.de.game.object.entity.Player;
import me.xxfreakdevxx.de.game.object.entity.Zombie;

public class World {
	
	public String worldname = "overworld";
	public String seed = "";
	public WorldSize size = WorldSize.XX_SMALL;
	private ConcurrentHashMap<String, Block> blocks = new ConcurrentHashMap<String, Block>();
	private ConcurrentLinkedQueue<Zombie> zombies = new ConcurrentLinkedQueue<Zombie>();
	private ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<Entity>();
	public boolean isGenerating = false;
	public boolean isGenerated = false;
	public boolean showRaster = false;
	public Player player = null;
	public Physics physics = null;
	public String status = "";
	public static World world = null;
	public static int blocksize = SquareCraft.blocksize;
	
	public WorldGenerator generator = null;
	
	public World(WorldSize size) {
		this.size = size;
		this.physics = new Physics(this); 
		this.generator = new WorldGenerator(this);
		World.world = this;
	}
	
	protected int border_distance_x = 0;
	protected int border_distance_y = 30;
	public void regenerate() {
		world = new World(size);
		isGenerated = false;
		player = null;
		blocks.clear();
		ChunkManager.clearChunks();
		generate();
	}
	public void generate() {
		SquareCraft.log("World", "Generating World");
		isGenerating = true;
		generator.generate();
		spawnPlayer();
		AnimalSpawner.spreadAnimals();
//		SquareCraft.log("World", "Generated Blocks: "+blocks.keySet().toString());
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
	public Block getBlockAt(int x, int y) { 
		return getBlockAt(x+":"+y);
	}
	public boolean setBlock(Block block) {
		if(blocks.containsKey(block.getLocation().getLocationString())) blocks.remove(block.getLocation().getLocationString());
		blocks.put(block.getLocation().getLocationString(), block);
		int chunk = (int)(block.getLocation().getIntX(false) / ChunkManager.chunksizePixels);
		if(isGenerated) {
			ChunkManager.getChunk(chunk).setBlock(block);
			if(ChunkManager.getChunk(chunk) != null) {
			}else System.out.println("Chunk = null");			
		}
		return true;
	}
	public boolean removeBlock(String loc_string) {
		if(blocks.containsKey(loc_string)) blocks.remove(loc_string);
		else return false;
		return true;
	}
	public Block getHighestBlockAt(int x) {
		Block block = null;
		for(int i = 0; i != size.getHeight(); i++) {
			block = getBlockAt(x+":"+(i*blocksize));
			if(block.getMaterial() != Material.AIR) return block;
		}
		return null;
	}	
	private int width_blocks_amount = 0;//Wie viele Blöcke passen in die breite des Bildschirmes? //Gehört zur alten Render Methode
	private int height_blocks_amount = 0;//Wie viele Blöcke passen in die Höhe des Bildschirmes? //Gehört zur alten Render Methode
	private int x_r = 0;//Gehört zur alten Render Methode
	private int y_r = 0;//Gehört zur alten Render Methode
	private int bsize = blocksize;//Gehört zur alten Render Methode
	private Block render_block = null; //Gehört zur alten Render Methode
	private Camera cam = SquareCraft.getCamera();//Gehört zur alten Render Methode
	
	private int rendered_blocks = 0;
	private Location location = new Location(this, 0d, 0d);
	private double x_ent = 0d;
	private double y_ent = 0d;
	public void render(Graphics g) {
		if(isGenerated == true) {
			g.drawImage(TextureAtlas.getTexture("welt-bg"), 0,0, SquareCraft.windowWidth, SquareCraft.windowHeight, null);
			//Es darf keine Camera Coords addiert werden in der Render Methode!
			//Des werden nur die Blöcke gerendert, die im Bildschirm sind
			rendered_blocks = 0;
			width_blocks_amount = (int)(SquareCraft.windowWidth / blocksize);
			height_blocks_amount = (int)(SquareCraft.windowHeight / blocksize);
			
			/* Alte Render Methode */
			for(int x = -1; x != width_blocks_amount+2; x++) {
				for(int y = -1; y != height_blocks_amount+2; y++) {
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
			/* ENDE: Alte Render Methode */
			
			/* Alte Render Methode */
//		for(int x = -1; x != width_blocks_amount+1; x++) {
//			for(int y = -1; y != height_blocks_amount+1; y++) {
//				x_r = (int)(cam.getX()+(x*bsize));
//				y_r = (int)(cam.getY()+(y*bsize));
//				x_r = Location.fixToRaster(x_r);
//				y_r = Location.fixToRaster(y_r);
//				location.setXY(x_r, y_r);
//				render_block = blocks.get(location.getLocationString());
//				
//				if(render_block == null) /*System.out.println("Block == null")*/;
//				else {
//					rendered_blocks++;
//					render_block.render(g);
//				}
//			}
//			
//		}
			/* ENDE: Alte Render Methode */
			
		}
		status = "Rendered Blocks: "+rendered_blocks;
		status = status+" LOC:"+player.getLocation().getLocationString();
		for(Zombie z : zombies) z.render(g);
		if(player != null) player.render(g);
		for(Entity ent : entities) {
			x_ent = ent.getLocation().getIntX(false);
			y_ent = ent.getLocation().getIntY(false);
			if(SquareCraft.isLocInOnScrren(x_ent, y_ent)) ent.render(g);
		}
	}
	
	public void tick() {
		Block block = null;
		for(int x = -1; x != width_blocks_amount+1; x++) {
			for(int y = -1; y != height_blocks_amount+1; y++) {
				x_r = (int)(cam.getX()+(x*bsize));
				y_r = (int)(cam.getY()+(y*bsize));
				x_r = Location.fixToRaster(x_r);
				y_r = Location.fixToRaster(y_r);
				location.setXY(x_r, y_r);
				block = blocks.get(location.getLocationString());
				
				if(block == null) /*System.out.println("Block == null")*/;
				else {
					block.tick();
				}
			}
		}
		if(player != null) player.tick();
		for(Zombie z : zombies) z.tick();
		if(player != null) SquareCraft.getCamera().tick(player);
		for(Entity ent : entities) if(ent != null) ent.tick();
		physics.applyGravity();
	}
	public void listBlocks() {
		for(String s : blocks.keySet()) {
			SquareCraft.log("World", "Key: "+s+" Block: "+blocks.get(s).getMaterial().getName());
		}
	}
	
	public void spawnPlayer() {
//		Block b = null;
//		int x = 0;
//		while(b == null) {
//			x = SquareCraft.randomInteger(0+10, size.worldwidth-10);
//			b = getHighestBlockAt(x);	
//			if(getHighestBlockAt(x) == null) System.out.println("BLOCK == NULL");
//		}
//		player = new Player(new Location(x*blocksize, b.getLocation().getY(false) - 2*blocksize));
		player = new Player(new Location(SquareCraft.randomInteger(60, 70)*blocksize, 0));
		player.setWorld(this);
	}
	public boolean spawnEntity(Entity ent) {
		if(entities.contains(ent) == false) {
			if(ent instanceof Item) System.out.println("Item spawned!");
			entities.add(ent);
			return true;
		}else return false;
	}
	public ConcurrentLinkedQueue<Entity> getEntities() {
		return this.entities;
	}
	public boolean removeEntity(Entity entity) {
		if(entities.contains(entity)) {
			this.entities.remove(entity);
			return true;
		}else return false;
	}
	
	public static World getWorld() {
		return world;
	}
	
	public static enum WorldSize {
		
		XXX_SMALL(2,2),
		XX_SMALL(9,9),
		X_SMALL(20,10),
		SMALL(240,200),
		MEDIUM(100,50),
		LARGE(1200,500),
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
		public Random ran = new Random();
		
		public WorldGenerator(World world) {
			this.world = world;
			y = world.border_distance_y;
			program = new FlatProgram(world);
		}
		
		public void generate() {
			//Schritt 1
			program.generate();
			for(int x : program.getPoints().keySet()) buildLine(x, program.getPoints().get(x));

			//Schritt 2
			if(program instanceof SmoothProgram) ((SmoothProgram)program).smoothAll();
			else if(program instanceof FlatProgram) ((FlatProgram)program).smoothAll();
//			System.out.println("points: "+program.getPoints().size());
			
			//Schritt 3
			for(int x = 0; x != size.worldwidth/ChunkManager.chunksizeBlocks+1; x++) {
				ChunkManager.claimBlocks((x));
			}
		}

		public void buildLine(int x, int y) {
			int height = World.getWorld().size.getHeight();
			int dirt_range = 1;
			for(int i = 0; i != height-y; i++) {
				dirt_range = randomInteger(34, 35);
				if(i == 0) {
					world.setBlock(new GrassBlock(new Location(x*blocksize, (y+i)*blocksize)));
				}else if(i == 1) {
					for(int yi = 0; yi < dirt_range; yi++) {
						if(yi!=0) i++;
						world.setBlock(new DirtBlock(new Location(x*blocksize, (y+i)*blocksize)));
					}
				}else world.setBlock(new StoneBlock(new Location(x*blocksize, (y+i)*blocksize)));
			}
		}

		public double randomDouble(double min, double max) {
		    if (min >= max) {
		        throw new IllegalArgumentException("max must be greater than min");
		    }
		    return min + (max - min) * ran.nextDouble();
		}
		public int randomInteger(int min, int max) {
		    if (min >= max) {
		        throw new IllegalArgumentException("max must be greater than min");
		    }
		    return ran.nextInt((max - min) + 1) + min;
		}
		
		public class SmoothProgram extends Program {

			public SmoothProgram(World world) {
				super(world);
			}
			
			private int normal_null = (SquareCraft.windowHeight)+1+((size.worldheight+border_distance_y)*blocksize);
			private int r = 0;
			
			@Override
			public void generate() {
				int tick = 0;
				for(int i = 0; i != size.getWidth(); i++) {
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
		public class FlatProgram extends Program {

			public FlatProgram(World world) {
				super(world);
			}
			
			private int normal_null = (SquareCraft.windowHeight)+1+((size.worldheight+border_distance_y)*blocksize);
			private int r = 0;
			
			@Override
			public void generate() {
				int tick = 0;
				for(int i = 0; i != size.getWidth(); i++) {
				    if(tick == 50) {
				        r = randomInteger(0, 2);
				        tick = 0;
				    }else{
				        r = randomInteger(0, 1);
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
			
			public Program(World world) {
				this.world = world;
			}
			public abstract void generate();
			
			//Points beinhaltet die Locations, an denen eine Reihe von Blöcken in Richtung Y+ gebildert werden sollen
			protected HashMap<Integer, Integer> getPoints() {
				return points;
			}
			
		}
	}
	
	public static class GeneratorSettings {
		
		public static int min_dirt_range = 3;
		public static int max_dirt_range = 10;
		
	}

	
	public static class ChunkManager {
		
		public static HashMap<Integer, Chunk> chunks = new HashMap<Integer, Chunk>();
		public static int chunksizePixels = 6*blocksize;
		public static int chunksizeBlocks = chunksizePixels / blocksize;
		
		public ChunkManager() {
			
		}
		
		public static Chunk claimBlocks(int id)  {
			Chunk chunk = new Chunk(id);
//			System.out.println("Generating Chunk ID: "+id);
			chunk.claimBlocks();
			chunks.put(id, chunk);
			return chunk;
		}
		public static void setChunk(Chunk chunk) {
			if(chunks.containsKey(chunk.getId())) {
				chunks.get(chunk.getId()).clear();
				chunks.remove(chunk.getId());
				chunks.put(chunk.getId(), chunk);				
			}
		}
		public static Chunk getChunk(int id) {
			return chunks.get(id);
		}
		public static int c = 0;
		public static void render(Graphics g) {
			for(int i : chunks.keySet()) chunks.get(i).renderChunk(g);
//			chunks.get(c).renderChunk(g);
		}
		public static void replaceChunk(int id, Chunk chunk) {
			chunks.replace(id, chunk);
		}
		public static void clearChunks() {
			chunks.clear();
		}
		public static void clearChunk(int id) {
			chunks.get(id).clear();
		}
		public static HashMap<Integer, Chunk> getChunks() {
			return chunks;
		}
		
		
		
		
		public static class Chunk {
			private int blocksize = SquareCraft.blocksize;
			private int id = 0;
			private ConcurrentHashMap<String, Block> blocks = new ConcurrentHashMap<String, Block>();
			private boolean highlightChunk = true;
			
			public Chunk(int id) {
				this.id = id;
			}
			public boolean removeBlock(String key) {
				if(blocks.containsKey(key)) blocks.remove(key);
				else return false;
				return true;
			}
			public boolean setBlock(Block block)  {
				if(blocks.containsKey(block.getLocation().getLocationString()) && blocks.get(block.getLocation().getLocationString()).getMaterial() != Material.AIR) return false;
				else if(blocks.containsKey(block.getLocation().getLocationString()) && blocks.get(block.getLocation().getLocationString()).getMaterial() == Material.AIR) {
					blocks.remove(block.getLocation().getLocationString());
					blocks.put(block.getLocation().getLocationString(), block);
				} else blocks.put(block.getLocation().getLocationString(), block);
				return true;
			}
			public ConcurrentHashMap<String, Block> getBlocks() {
				return blocks;
			}
			public Chunk claimBlocks() {
				Location loc = null;
				Block block = null;
				for(int x = 0; x < chunksizeBlocks; x++) {
					for(int y = 0; y < (getWorld().size.worldheight); y++) {
						loc = new Location(x*blocksize+(id*chunksizePixels), y*blocksize);
						
						block = getWorld().getBlockAt(loc.getLocationString());
						blocks.put(loc.getLocationString(), block);
						block.setChunk(this.id);
//						System.out.println("Claimed: "+loc.getLocationString());
					}
				}
				
				return this;
			}
			Color fieldColor = new Color(1f,0f,0f,0.3f);
			public void renderChunk(Graphics g) {
				for(String s : blocks.keySet()) {
					if(blocks.get(s) == null) blocks.remove(s);
					else blocks.get(s).render(g);
				}
				if(highlightChunk) {
					g.setColor(fieldColor);
					g.fillRect((int)(id*chunksizePixels-SquareCraft.getCamera().getX())+2,
							(int)(0-SquareCraft.getCamera().getY()),
							chunksizePixels-2,
							getWorld().size.worldheight*blocksize);
				}
			}
			
			public int getId() {
				return id;
			}
			
			public void clear() {
				for(String loc : blocks.keySet()) {
					getWorld().removeBlock(loc);
				}
				blocks.clear();
			}
			public Block getHighestBlockAt(int x) {
				//Hier wird die X in Pixel gebraucht, nicht in BlockID
				for(int i = 0; i != world.size.worldheight; i++) {
					if(blocks.contains(x+":"+(i*blocksize)) && blocks.get(x+":"+(i*blocksize)).getMaterial() == Material.GRASS) {
						 blocks.get(x+":"+(i*blocksize)).select();
						return blocks.get(x+":"+(i*blocksize));
					}
				}
				return new AirBlock(new Location(x,0));
			}
			public Block getHighestBlockAt(int x, Material material) {
				//Hier wird die X in Pixel gebraucht, nicht in BlockID
				for(int i = 0; i != world.size.worldheight; i++) {
					if(blocks.contains(x+":"+(i*blocksize)) && blocks.get(x+":"+(i*blocksize)).getMaterial() == material) {
						System.out.println("JA");
						 blocks.get(x+":"+(i*blocksize)).select();
						return blocks.get(x+":"+(i*blocksize));
					}
				}
				return new AirBlock(new Location(x,4));
			}
			
			public void generateOres() {
				//TODO:  
			}
		}
		
	}
	
}
