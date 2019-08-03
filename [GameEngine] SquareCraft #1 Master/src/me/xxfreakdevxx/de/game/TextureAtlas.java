package me.xxfreakdevxx.de.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import me.xxfreakdevxx.de.game.object.Material;

public class TextureAtlas {
	
	private static BufferedImage image;
	private static HashMap<String, BufferedImage> textures;
	
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
			textures.put(material.getName(), loadImage(material.getFilePath()));
		}
		textures.put("welt-bg.jpg", loadImage("/assets/textures/welt-bg.jpg"));	
	}
	
	public static BufferedImage getTexture(String key) {
		return textures.get(key);
	}
	
	public static HashMap<String, BufferedImage> getTextures() {
		return textures;
	}
	
}
