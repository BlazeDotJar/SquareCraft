package me.xxfreakdevxx.de.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import me.xxfreakdevxx.de.game.object.Material;

public class TextureAtlas {
	
	private static BufferedImage image;
	private static HashMap<String, BufferedImage> textures;
	public static String resource_pack_name = "sphax";
	
	public TextureAtlas() {
		TextureAtlas.textures=new HashMap<String, BufferedImage>();
		reloadTextures();
	}
	
	public static BufferedImage loadImage(String path) {
		try {
			System.out.println(path);
			image = ImageIO.read(TextureAtlas.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public static void reloadTextures() {
		/* Lädt alle Texturen neu und fügt sie zum Atlas(textures(hashmap)) hinzu */
		textures.clear();
		for(int i = 0; i < Material.values().length; i++) {
			Material material = Material.values()[i];
			textures.put(material.getName(), loadImage(material.getFilePath().replace("{RESOURCE_PACK}", resource_pack_name)));
		}
		textures.put("welt-bg", loadImage("/assets/{RESOURCE_PACK}/textures/welt-bg.jpg".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("sand_vein_1", loadImage("/assets/{RESOURCE_PACK}/textures/vein/sand_vein_1.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("sand_vein_2", loadImage("/assets/{RESOURCE_PACK}/textures/vein/sand_vein_2.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("sand_vein_3", loadImage("/assets/{RESOURCE_PACK}/textures/vein/sand_vein_3.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("player_anima", loadImage("/assets/{RESOURCE_PACK}/textures/entity/player_anima.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("pig_anima", loadImage("/assets/{RESOURCE_PACK}/textures/entity/pig_anima.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("healthbar", loadImage("/assets/{RESOURCE_PACK}/textures/indicator/healthbar.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("chicken", loadImage("/assets/{RESOURCE_PACK}/textures/entity/chicken.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("player_head", loadImage("/assets/{RESOURCE_PACK}/textures/entity/player_head.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("arrow", loadImage("/assets/{RESOURCE_PACK}/textures/item/arrow.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("damage_stage_0", loadImage("/assets/{RESOURCE_PACK}/textures/destroy_stage_0.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("damage_stage_1", loadImage("/assets/{RESOURCE_PACK}/textures/destroy_stage_1.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("damage_stage_2", loadImage("/assets/{RESOURCE_PACK}/textures/destroy_stage_2.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("damage_stage_3", loadImage("/assets/{RESOURCE_PACK}/textures/destroy_stage_3.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("damage_stage_4", loadImage("/assets/{RESOURCE_PACK}/textures/destroy_stage_4.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("damage_stage_5", loadImage("/assets/{RESOURCE_PACK}/textures/destroy_stage_5.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("damage_stage_6", loadImage("/assets/{RESOURCE_PACK}/textures/destroy_stage_6.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("damage_stage_7", loadImage("/assets/{RESOURCE_PACK}/textures/destroy_stage_7.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("damage_stage_8", loadImage("/assets/{RESOURCE_PACK}/textures/destroy_stage_8.png".replace("{RESOURCE_PACK}", resource_pack_name)));
		textures.put("damage_stage_9", loadImage("/assets/{RESOURCE_PACK}/textures/destroy_stage_9.png".replace("{RESOURCE_PACK}", resource_pack_name)));
	}
	
	public static BufferedImage getTexture(String key) {
		return textures.get(key);
	}
	
	public static HashMap<String, BufferedImage> getTextures() {
		return textures;
	}
	
}
