package me.xxfreakdevxx.de.game.inventory;

import me.xxfreakdevxx.de.game.object.Material;

public class ItemStack {
	
	protected Material material = Material.AIR;
	protected int amount = 1;
	protected int durability = 0;
	protected int max_durability = 0;
	
	public ItemStack(Material material, int amount) {
		this.material = material;
		if(material == Material.AIR) amount = 0;
		else this.amount = amount;
	}
	public ItemStack(Material material) {
		this.material = material;
		if(material == Material.AIR) this.amount = 0;
	}
	public ItemStack setAmount(int amount) {
		if(material == Material.AIR) amount = 0;
		else this.amount = amount;
		return this;
	}
	public int getAmount() { return amount; }
	public ItemStack setMaterial(Material material) {
		this.material = material;
		return this;
	}
	public Material getMaterial() { return material; }
	
	public ItemStack clone() {
		return new ItemStack(Material.values()[material.getId()], amount);
	}
}
