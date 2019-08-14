package me.xxfreakdevxx.de.game.object;

import java.awt.image.BufferedImage;

import me.xxfreakdevxx.de.game.TextureAtlas;

public enum Material {
	
	AIR(0, "air", "Luft", false, "", ""),
	STONE(1, "stone", "Stein", true, "/assets/{RESOURCE_PACK}/textures/block/stone_2.png", ""),
	DIRT(2, "dirt", "Dreck", true, "/assets/{RESOURCE_PACK}/textures/block/dirt_2.png", ""),
	GRASS(3, "gras", "Gras", true, "/assets/{RESOURCE_PACK}/textures/block/grass_2.png", ""),
	SAND(4, "sand", "Sand", true, "/assets/{RESOURCE_PACK}/textures/block/sand.png", ""),
	IRON_ORE(5, "iron_ore", "Eisenerz", true, "/assets/{RESOURCE_PACK}/textures/block/iron_ore.png", ""),
	COAL_ORE(6, "coal_ore", "Kohleerz", true, "/assets/{RESOURCE_PACK}/textures/block/coal_ore.png", ""),
	TNT(7, "tnt", "Sprengstoff", true, "/assets/{RESOURCE_PACK}/textures/block/tnt.png", "");
	
	int id = 0;
	String name, displayname, filepath, audiofilepath;
	boolean isSolid;
	
	Material(int id, String name, String displayname, boolean isSolid, String filepath, String audiofilepath) {
		this.id=id;
		this.name=name;
		this.displayname=displayname;
		this.isSolid=isSolid;
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
	
	public String getFilePath() {
		return filepath;
	}

	public String getSoundFilePath() {
		return audiofilepath;
	}

	public BufferedImage getTexture() {
		return TextureAtlas.getTexture(getName());
	}
	
}
