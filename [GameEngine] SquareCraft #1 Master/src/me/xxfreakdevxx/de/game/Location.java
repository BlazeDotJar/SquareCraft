package me.xxfreakdevxx.de.game;

import me.xxfreakdevxx.de.game.environment.World;

public class Location {
	
	public double xCurrent;
	public double yCurrent;
	private double xPrev, yPrev;
	private World world = null;
	
	public Location(String loc_string) {
		String[] split = loc_string.split(":");
		this.xCurrent=Integer.parseInt(split[0]);
		this.yCurrent=Integer.parseInt(split[1]);
	}
	public Location(double x, double y) {
		this.xCurrent=x;
		this.yCurrent=y;
	}
	public Location(World world, double x, double y) {
		this.xCurrent=x;
		this.yCurrent=y;
		this.world = world;
	}
	public Location() {}
	public Location clone() {
		return new Location(world, getX(false), getY(false));
	}
	public static int fixToRaster(int i) {
		return (i/SquareCraft.blocksize)*SquareCraft.blocksize;
	}
	public Location fixLocationToRaster() {
		this.xCurrent = (int) (this.xCurrent/SquareCraft.blocksize)*SquareCraft.blocksize;
		this.yCurrent = (int) (this.yCurrent/SquareCraft.blocksize)*SquareCraft.blocksize;
		return this;
	}
	public void setFixYtoRaster() {
		this.yCurrent = fixToRaster((int)yCurrent);
	}
	public void setFixXtoRaster() {
		this.xCurrent = fixToRaster((int)xCurrent);
	}
	
	//TODO: Location String
	public String getConvertedLocationString() {
		return getBlockIntX()+":"+getBlockIntY();
	}
	public String getLocationString() {
		return getIntX(false)+":"+getIntY(false);
	}
	
	//TODO: Add X or Y
	public Location add(double x, double y) {
		this.xPrev=xCurrent;
		this.yPrev=yCurrent;
		this.xCurrent+=x;
		this.yCurrent+=y;
		return this.clone();
	}
	public Location addAndConvert(double xD, double yD) {
		xD*=SquareCraft.blocksize;
		yD*=SquareCraft.blocksize;
		this.xPrev=xCurrent;
		this.yPrev=yCurrent;
		this.xCurrent+=xD;
		this.yCurrent+=yD;
		return this.clone();
	}
	
	//TODO: Set X or Y or World
	public Location setWorld(World world) {
		this.world = world;
		return this.clone();
	}
	public Location setX(double x) {
		this.xPrev=xCurrent;
		this.xCurrent=x;
		return this.clone();
	}
	public Location setY(double y) {
		this.yPrev=yCurrent;
		this.yCurrent=y;
		return this.clone();
	}
	public Location setXY(double x, double y) {
		this.xPrev=xCurrent;
		this.xCurrent=x;
		this.yPrev=yCurrent;
		this.yCurrent=y;
		return this.clone();
	}
	public Location setAndConvert(double xD, double yD) {
		this.xPrev=xCurrent;
		this.yPrev=yCurrent;
		this.xCurrent=xD;
		this.yCurrent=yD;
		this.fixLocationToRaster();
		return this.clone();
	}
	public Location add(Location loc) {
		this.xPrev=xCurrent;
		this.yPrev=yCurrent;
		this.xCurrent+=loc.getX(false);
		this.yCurrent+=loc.getY(false);
		return this;
	}
	
	//TODO: Get World or X or Y
	public World getWorld() {
		return world;
	}
	public double getX(boolean addCameraX) {
		if(addCameraX) return (xCurrent - SquareCraft.getCamera().getX());
		else return xCurrent;
	}
	public double getY(boolean addCameraY) {
		if(addCameraY) return (yCurrent - SquareCraft.getCamera().getY());
		else return yCurrent;
	}
	public int getIntX(boolean addCameraX) {
		if(addCameraX) return ((int)(xCurrent - SquareCraft.getCamera().getX()));
		else return (int)xCurrent;
	}
	public int getIntY(boolean addCameraY) {
		if(addCameraY) return ((int)(yCurrent - SquareCraft.getCamera().getY()));
		else return (int)yCurrent;
	}
	public int getBlockIntX() {
		int x_cur = (int) xCurrent;
		int ax = ((int)(x_cur / SquareCraft.blocksize));
		return ax;
	}
	public int getBlockIntY() {
		int y_cur = (int) yCurrent;
		int ay = ((int)(y_cur / SquareCraft.blocksize));
		return ay;
	}
	public double getPreviousX() {
		return xPrev;
	}
	public double getPreviousY() {
		return yPrev;
	}	
	public int getIntPreviousX() {
		return (int)xPrev;
	}
	public int getIntPreviousY() {
		return (int)yPrev;
	}
}
