package me.xxfreakdevxx.de.game.object;

import java.awt.image.BufferedImage;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.object.block.AirBlock;
import me.xxfreakdevxx.de.game.object.block.Block;
import me.xxfreakdevxx.de.game.object.block.CoalOreBlock;
import me.xxfreakdevxx.de.game.object.block.DirtBlock;
import me.xxfreakdevxx.de.game.object.block.GrassBlock;
import me.xxfreakdevxx.de.game.object.block.IronOreBlock;
import me.xxfreakdevxx.de.game.object.block.SandBlock;
import me.xxfreakdevxx.de.game.object.block.StoneBlock;
import me.xxfreakdevxx.de.game.object.block.TNTBlock;

public enum Material {
	
	AIR(0, "air", "Luft", 999, false, true, "", ""),
	STONE(1, "stone", "Stein", 25, true, true, "/assets/{RESOURCE_PACK}/textures/block/stone_2.png", ""),
	DIRT(2, "dirt", "Dreck", 15, true, true, "/assets/{RESOURCE_PACK}/textures/block/dirt_2.png", ""),
	GRASS(3, "gras", "Gras", 35, true, true, "/assets/{RESOURCE_PACK}/textures/block/grass_2.png", ""),
	SAND(4, "sand", "Sand", 20, true, true, "/assets/{RESOURCE_PACK}/textures/block/sand.png", ""),
	IRON_ORE(5, "iron_ore", "Eisenerz", 30, true, true, "/assets/{RESOURCE_PACK}/textures/block/iron_ore.png", ""),
	COAL_ORE(6, "coal_ore", "Kohleerz", 35, true, true, "/assets/{RESOURCE_PACK}/textures/block/coal_ore.png", ""),
	TNT(7, "tnt", "Sprengstoff", 80, true, true, "/assets/{RESOURCE_PACK}/textures/block/tnt.png", ""),
	BOW(8, "bow", "Bogen", 10, false, false, "", ""),
	ARROW(9, "arrow", "Pfeil", 10, false, false, "/assets/{RESOURCE_PACK}/textures/item/arrow.png", "");
	
	int id, health;
	String name, displayname, filepath, audiofilepath;
	boolean isSolid, isBlock;
	Block block;
	
	Material(int id, String name, String displayname, int health, boolean isSolid, boolean isBlock, String filepath, String audiofilepath) {
		this.id=id;
		this.name=name;
		this.displayname=displayname;
		this.health=health;
		this.isSolid=isSolid;
		this.isBlock=isBlock;
		this.filepath=filepath;
		this.audiofilepath=audiofilepath;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDisplayname() {
		return displayname;
	}
	
	public int getHealth() {
		return health;
	}
	
	public boolean isSolid() {
		return isSolid;
	}
	
	public boolean isBlock() {
		return isBlock;
	}
	
	public String getFilePath() {
		return filepath;
	}

	public String getSoundFilePath() {
		return audiofilepath;
	}

	public BufferedImage getTexture() {
		return TextureAtlas.getTexture(getName());
	}
	
	public void setBlock(Block block) {
		this.block = block;
	}

	public Block getBlock() {
		return block;
	}
	
	public static void setBlocks()  {
		Material.AIR.setBlock(new AirBlock(new Location(0d,0d)));
		Material.STONE.setBlock(new StoneBlock(new Location(0d,0d)));
		Material.DIRT.setBlock(new DirtBlock(new Location(0d,0d)));
		Material.GRASS.setBlock(new GrassBlock(new Location(0d,0d)));
		Material.SAND.setBlock(new SandBlock(new Location(0d,0d)));
		Material.IRON_ORE.setBlock(new IronOreBlock(new Location(0d,0d)));
		Material.COAL_ORE.setBlock(new CoalOreBlock(new Location(0d,0d)));
		Material.TNT.setBlock(new TNTBlock(new Location(0d,0d)));
	}
	
}
