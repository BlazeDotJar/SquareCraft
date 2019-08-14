package me.xxfreakdevxx.de.game.environment;

import me.xxfreakdevxx.de.game.Location;
import me.xxfreakdevxx.de.game.SquareCraft;
import me.xxfreakdevxx.de.game.object.entity.Chicken;
import me.xxfreakdevxx.de.game.object.entity.Pig;

public class AnimalSpawner {
	
	static int max_pig_in_chunk = 3;
	static int max_chicken_in_chunk = 3;
	
	public static void spreadAnimals() {
		int a = 0;
		for(int x = 0; x != World.getWorld().size.getWidth(); x+=16) {
			if(SquareCraft.ran.nextBoolean()) {
				a = SquareCraft.randomInteger(0, max_pig_in_chunk);
				for(int i = 0; i != a; i++)World.getWorld().entities.add(new Pig(new Location(World.getWorld(), (x+SquareCraft.randomInteger(0, 16))*SquareCraft.blocksize, 0)));
			}
		}
		for(int x = 0; x != World.getWorld().size.getWidth(); x+=16) {
			if(SquareCraft.ran.nextBoolean()) {
				a = SquareCraft.randomInteger(0, max_chicken_in_chunk);
				for(int i = 0; i != a; i++)World.getWorld().entities.add(new Chicken(new Location(World.getWorld(), (x+SquareCraft.randomInteger(0, 16))*SquareCraft.blocksize, 0)));
			}
		}
	}
	
}
