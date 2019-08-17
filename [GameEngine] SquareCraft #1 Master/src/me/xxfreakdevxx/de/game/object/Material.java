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
	
	AIR(0, "air", "Luft", false, true, "", ""),
	STONE(1, "stone", "Stein", true, true, "/assets/{RESOURCE_PACK}/textures/block/stone_2.png", ""),
	DIRT(2, "dirt", "Dreck", true, true, "/assets/{RESOURCE_PACK}/textures/block/dirt_2.png", ""),
	GRASS(3, "gras", "Gras", true, true, "/assets/{RESOURCE_PACK}/textures/block/grass_2.png", ""),
	SAND(4, "sand", "Sand", true, true, "/assets/{RESOURCE_PACK}/textures/block/sand.png", ""),
	IRON_ORE(5, "iron_ore", "Eisenerz", true, true, "/assets/{RESOURCE_PACK}/textures/block/iron_ore.png", ""),
	COAL_ORE(6, "coal_ore", "Kohleerz", true, true, "/assets/{RESOURCE_PACK}/textures/block/coal_ore.png", ""),
	TNT(7, "tnt", "Sprengstoff", true, true, "/assets/{RESOURCE_PACK}/textures/block/tnt.png", "");
	
	int id;
	String name, displayname, filepath, audiofilepath;
	boolean isSolid;
	boolean isBlock;
	Block block;
	
	Material(int id, String name, String displayname, boolean isSolid, boolean isBlock, String filepath, String audiofilepath) {
		this.id=id;
		this.name=name;
		this.displayname=displayname;
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
