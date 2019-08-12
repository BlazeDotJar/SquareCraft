package me.xxfreakdevxx.de.game.object.entity.health;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.TextureAtlas;
import me.xxfreakdevxx.de.game.object.entity.Entity;

public class HealthIndicator {
	
	public Entity target = null;
	public int width = 200;
	public int height = 20;
	public int x = SquareCraft.windowWidth-width-20;
	public int y = 20;
	public int calc_width = 0;
	public BufferedImage bar_src = null;
	public BufferedImage bar = null;
	public Color bg_color = new Color(0f,0f,0f,0.3f);
	
	public HealthIndicator(Entity target) {
		this.target = target;
		this.bar_src = TextureAtlas.getTexture("healthbar");
		this.calc_width = (width - (((int)target.getMaxHealth() - (int)target.getHealth()) * (int)(width / target.getMaxHealth())));
	}
	
	public void render(Graphics g) {
		g.drawImage(bar, x, y, calc_width, height, null);
		g.setColor(bg_color);
		g.fillRect(x+3, y+3, calc_width-6, 14);
		g.setColor(Color.WHITE);
		g.drawString((int)target.getHealth()+" HP", x+5, y+14);
	}
	
	public void tick() {
		x = SquareCraft.windowWidth-width-20;
		y = 20;
		int one_percent = (int)(width / target.getMaxHealth());
		if((width - (((int)target.getMaxHealth() - (int)target.getHealth()) * one_percent)) < calc_width) {			
			this.calc_width = (width - (((int)target.getMaxHealth() - (int)target.getHealth()) * one_percent));
			if(calc_width <= 0) calc_width = 1;
			bar = bar_src.getSubimage(0, 0, calc_width, 20);
		}
	}
	
}
