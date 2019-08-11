package me.xxfreakdevxx.de.game.environment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
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
import me.xxfreakdevxx.de.game.object.block.CoalOreBlock;
import me.xxfreakdevxx.de.game.object.block.DirtBlock;
import me.xxfreakdevxx.de.game.object.block.GrassBlock;
import me.xxfreakdevxx.de.game.object.block.IronOreBlock;
import me.xxfreakdevxx.de.game.object.block.SandBlock;
import me.xxfreakdevxx.de.game.object.block.StoneBlock;
import me.xxfreakdevxx.de.game.object.entity.Player;
import me.xxfreakdevxx.de.game.object.entity.Zombie;

public class World2 {
	
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
	public static World2 world = null;
	
	public WorldGenerator generator = null;
	
	public World2(WorldSize size) {
		this.size = size;
		this.physics = new Physics(this); 
		this.generator = new WorldGenerator(this);
		World2.world = this;
	}
	
	protected int border_distance_x = 0;
	protected int border_distance_y = 10;
	public void regenerate() {
		world = new World2(size);
		isGenerated = false;
		player = null;
		blocks.clear();
		ChunkManager.clearChunks();
		generate();
		spawnPlayer();
	}
	public void generate() {
		SquareCraft.log("World", "Generating World");
		isGenerating = true;
		generator.generate();
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
			if(ChunkManager.getChunk(chunk) != null) {
				ChunkManager.getChunk(chunk).setBlock(block);
			}else System.out.println("Chunk = null");			
		}
		return true;
	}
	public boolean removeBlock(String loc_string) {
		if(blocks.containsKey(loc_string)) blocks.remove(loc_string);
		else return false;
		return true;
	}
	
	private int width_blocks_amount = 0;//Wie viele Blöcke passen in die breite des Bildschirmes? //Gehört zur alten Render Methode
	private int height_blocks_amount = 0;//Wie viele Blöcke passen in die Höhe des Bildschirmes? //Gehört zur alten Render Methode
	private int x_r = 0;//Gehört zur alten Render Methode
	private int y_r = 0;//Gehört zur alten Render Methode
	private int bsize = SquareCraft.blocksize;//Gehört zur alten Render Methode
	private Block render_block = null; //Gehört zur alten Render Methode
	private Camera cam = SquareCraft.getCamera();//Gehört zur alten Render Methode
	
	private int rendered_blocks = 0;
	private Location location = new Location(this, 0d, 0d);;
	public void render(Graphics g) {
		if(isGenerated == false) return;
		g.drawImage(TextureAtlas.getTexture("welt-bg"), 0,0, SquareCraft.windowWidth, SquareCraft.windowHeight, null);
		//Es darf keine Camera Coords addiert werden in der Render Methode!
		//Des werden nur die Blöcke gerendert, die im Bildschirm sind
		rendered_blocks = 0;
		width_blocks_amount = (int)(SquareCraft.windowWidth / SquareCraft.blocksize);
		height_blocks_amount = (int)(SquareCraft.windowHeight / SquareCraft.blocksize);
		
		/* Alte Render Methode */
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
		
		status = "Rendered Blocks: "+rendered_blocks;
		status = status+" LOC:"+location.getLocationString();
		for(Zombie z : zombies) z.render(g);
		if(player != null) player.render(g);
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
	
	public static World2 getWorld() {
		return world;
	}
	
	public static enum WorldSize {
		
		XXX_SMALL(2,2),
		XX_SMALL(9,9),
		X_SMALL(20,10),
		SMALL(240,180),
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
		private World2 world = null;
		public Random ran = new Random();
		
		public WorldGenerator(World2 world) {
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
			new OreGenerator().generate();
		}
		public void buildLine(int x, int y) {
			int height = World2.getWorld().size.getHeight();
			int dirt_range = 1;
			for(int i = 0; i != height-y; i++) {
				dirt_range = randomInteger(34, 35);
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

			public SmoothProgram(World2 world) {
				super(world);
			}
			
			private int normal_null = (SquareCraft.windowHeight)+1+((size.worldheight+border_distance_y)*SquareCraft.blocksize);
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

			public FlatProgram(World2 world) {
				super(world);
			}
			
			private int normal_null = (SquareCraft.windowHeight)+1+((size.worldheight+border_distance_y)*SquareCraft.blocksize);
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
			protected World2 world = null;
			
			public Program(World2 world) {
				this.world = world;
			}
			public abstract void generate();
			
			//Points beinhaltet die Locations, an denen eine Reihe von Blöcken in Richtung Y+ gebildert werden sollen
			protected HashMap<Integer, Integer> getPoints() {
				return points;
			}
			
		}
		
		public class OreGenerator {
			
			private boolean sand = true;
			private boolean iron_ore = true;
			private boolean coal_ore = true;
			
			public void generate() {
				x = randomInteger(0, world.size.worldwidth);
				y = randomInteger(0, world.size.worldheight);
				if(sand) new SandPreset().generate();
				if(iron_ore) new IronOrePreset().generate();
				if(coal_ore) new CoalOrePreset().generate();
			}
			
			public abstract class OrePreset {
				
				protected int ore_size = 5;
				protected int x = 0;
				protected int y = 0;
				protected int amount = 10;
				protected BufferedImage vein_preset = null;
				protected Random ran = new Random();
				
				public abstract void generate();
				
				protected void generateRandomCoord() {
					x = randomInteger(0, world.size.worldwidth);
					y = randomInteger(0, world.size.worldheight);
					if(x >= world.size.worldwidth) x = world.size.worldwidth;
					if(y >= world.size.worldheight) y = world.size.worldheight;
				}
				
			}
			
			public class SandPreset extends OrePreset {
				
				private int preset_id = 1;
				private int amountofpresets = 30;
				private int y_height_min = 10*SquareCraft.blocksize;
				private int y_height_max = y_height_min+20*SquareCraft.blocksize;
				
				@Override
				public void generate() {
//					preset_id = randomInteger(0, amountofpresets);
					generatePreset();
				}
				
				public void generatePreset() {
					Location loc = null;
					int a = 0;
					for(int i = 0; i != amountofpresets; i++) {
						generateRandomCoord();
						int w = 0;
						int h = 0;
						switch(preset_id) {
						case 1:
							vein_preset = TextureAtlas.getTexture("sand_vein_2");
							w = vein_preset.getWidth();
							h = vein_preset.getHeight();
							loc = new Location((world.border_distance_x+x)*SquareCraft.blocksize, (y)*SquareCraft.blocksize); 
							
							for(int xx = 0; xx < w; xx++) {
								for(int yy = 0; yy < h; yy++) {
									int pixel = vein_preset.getRGB(xx, yy);
									int red = (pixel >> 16) & 0xff;
									int green = (pixel >> 8) & 0xff;
									int blue = (pixel) & 0xff;
									
									if(red == 0 && green == 0 && blue == 0 && ran.nextBoolean()) {
										loc.setXY((x+xx)*SquareCraft.blocksize, (border_distance_y+y+yy)*SquareCraft.blocksize);
										world.setBlock(new SandBlock(loc.clone()));
										a++;
									}
									if(green == 255) ;
									if(blue == 255) ;
								}
							}
							
							break;
						}
					}
					System.out.println("Sand generated: "+a);
				}
			}
			public class IronOrePreset extends OrePreset {
				
				private int preset_id = 1;
				private int amountofpresets = 300;
				private int y_height_min = 10*SquareCraft.blocksize;
				private int y_height_max = y_height_min+20*SquareCraft.blocksize;
				
				@Override
				public void generate() {
//					preset_id = randomInteger(0, amountofpresets);
					generatePreset();
				}
				
				public void generatePreset() {
					Location loc = null;
					int a = 0;
					int highest_y = 0;
					for(int i = 0; i != amountofpresets; i++) {
						generateRandomCoord();
						switch(preset_id) {
						case 1:
							//TODO: 
//							System.out.println("Chunk ID: "+(x/ChunkManager.chunksizeBlocks)+" Chunks: "+ChunkManager.getChunks().size());
							if(ChunkManager.getChunk(x/ChunkManager.chunksizeBlocks) == null) System.out.println("Chunk == null");
//							highest_y = 1900/SquareCraft.blocksize;
							highest_y = (int) ChunkManager.getChunk((x/ChunkManager.chunksizeBlocks)).getHighestBlockAt((x*SquareCraft.blocksize)).getLocation().clone().getY(false);
							System.out.println("Highest Block: "+highest_y);
							loc = new Location((x)*SquareCraft.blocksize, (highest_y+y)*SquareCraft.blocksize); 
							world.setBlock(new IronOreBlock(loc.clone()));
							if(ran.nextBoolean() && (x+1 < 0 == false) && (y < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(1d, 0d).clone()));
							if(ran.nextBoolean() && (x < 0 == false) && (y+1 < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(0d, 1d).clone()));
							if(ran.nextBoolean() && (x-1 < 0 == false) && (y < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(-1d, 0d).clone()));
							if(ran.nextBoolean() && (x-1 < 0 == false) && (y < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(-1d, 0d).clone()));
							if(ran.nextBoolean() && (x < 0 == false) && (y-1 < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(0d, -1d).clone()));
							if(ran.nextBoolean() && (x < 0 == false) && (y-1 < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(0d, -1d).clone()));
							if(ran.nextBoolean() && (x+1 < 0 == false) && (y < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(1d, 0d).clone()));
							if(ran.nextBoolean() && (x+1 < 0 == false) && (y < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(1d, 0d).clone()));
							if(ran.nextBoolean() && (x+1 < 0 == false) && (y < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(1d, 0d).clone()));
							if(ran.nextBoolean() && (x < 0 == false) && (y+1 < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(0d, 1d).clone()));
							if(ran.nextBoolean() && (x < 0 == false) && (y+1< 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(0d, 1d).clone()));
							if(ran.nextBoolean() && (x < 0 == false) && (y+1 < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(0d, 1d).clone()));
							if(ran.nextBoolean() && (x-1 < 0 == false) && (y < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(-1d, 0d).clone()));
							if(ran.nextBoolean() && (x-1 < 0 == false) && (y < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(-1d, 0d).clone()));
							if(ran.nextBoolean() && (x-1 < 0 == false) && (y < 0 == false)) world.setBlock(new IronOreBlock(loc.addAndConvert(-1d, 0d).clone()));
							break;
						}
					}
					System.out.println("Sand generated: "+a + "Highest: "+highest_y);
				}
			}
			public class CoalOrePreset extends OrePreset {
				
				private int preset_id = 1;
				private int amountofpresets = 300;
				private int y_height_min = 10*SquareCraft.blocksize;
				private int y_height_max = y_height_min+20*SquareCraft.blocksize;
				
				@Override
				public void generate() {
//					preset_id = randomInteger(0, amountofpresets);
					generatePreset();
				}
				
				public void generatePreset() {
					Location loc = null;
					int a = 0;
					int highest_y = 0;
					for(int i = 0; i != amountofpresets; i++) {
						generateRandomCoord();
						switch(preset_id) {
						case 1:
							//TODO:
//							System.out.println("Chunk ID: "+(x/ChunkManager.chunksizeBlocks)+" Chunks: "+ChunkManager.getChunks().size());
							if(ChunkManager.getChunk(x/ChunkManager.chunksizeBlocks) == null) System.out.println("Chunk == null");
//							highest_y = 1900/SquareCraft.blocksize;
							highest_y = (int) ChunkManager.getChunk((x/SquareCraft.blocksize)).getHighestBlockAt((x*SquareCraft.blocksize)).getLocation().getY(false);
							System.out.println("Highest Block: "+highest_y);
							loc = new Location((x)*SquareCraft.blocksize, (highest_y+y)*SquareCraft.blocksize); 
							world.setBlock(new CoalOreBlock(loc.clone()));
							if(ran.nextBoolean() && (x+1 < 0 == false) && (y < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(1d, 0d).clone()));
							if(ran.nextBoolean() && (x < 0 == false) && (y+1 < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(0d, 1d).clone()));
							if(ran.nextBoolean() && (x-1 < 0 == false) && (y < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(-1d, 0d).clone()));
							if(ran.nextBoolean() && (x-1 < 0 == false) && (y < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(-1d, 0d).clone()));
							if(ran.nextBoolean() && (x < 0 == false) && (y-1 < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(0d, -1d).clone()));
							if(ran.nextBoolean() && (x < 0 == false) && (y-1 < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(0d, -1d).clone()));
							if(ran.nextBoolean() && (x+1 < 0 == false) && (y < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(1d, 0d).clone()));
							if(ran.nextBoolean() && (x+1 < 0 == false) && (y < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(1d, 0d).clone()));
							if(ran.nextBoolean() && (x+1 < 0 == false) && (y < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(1d, 0d).clone()));
							if(ran.nextBoolean() && (x < 0 == false) && (y+1 < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(0d, 1d).clone()));
							if(ran.nextBoolean() && (x < 0 == false) && (y+1< 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(0d, 1d).clone()));
							if(ran.nextBoolean() && (x < 0 == false) && (y+1 < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(0d, 1d).clone()));
							if(ran.nextBoolean() && (x-1 < 0 == false) && (y < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(-1d, 0d).clone()));
							if(ran.nextBoolean() && (x-1 < 0 == false) && (y < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(-1d, 0d).clone()));
							if(ran.nextBoolean() && (x-1 < 0 == false) && (y < 0 == false)) world.setBlock(new CoalOreBlock(loc.addAndConvert(-1d, 0d).clone()));
							break;
						}
					}
					System.out.println("Sand generated: "+a + "Highest: "+highest_y);
				}
			}
		}
		
	}
	
	public static class GeneratorSettings {
		
		public static int min_dirt_range = 3;
		public static int max_dirt_range = 10;
		
	}

	
	public static class ChunkManager {
		
		public static HashMap<Integer, Chunk> chunks = new HashMap<Integer, Chunk>();
		public static int chunksizePixels = 6*SquareCraft.blocksize;
		public static int chunksizeBlocks = chunksizePixels / SquareCraft.blocksize;
		
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
			public boolean removeBlock(String key)  {
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
						loc = new Location(x*SquareCraft.blocksize+(id*chunksizePixels), y*SquareCraft.blocksize);
						
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
							getWorld().size.worldheight*SquareCraft.blocksize);
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
		}
		
	}
	
}
