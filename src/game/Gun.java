package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.WeaponItem;

public abstract class Gun extends WeaponItem {
	private int range;
	private double hitChance;
	private int shotDamage;

	public Gun(String name, char displayChar, int meleeDamage, int shotDamage, boolean singleTarget, double initHitChance) {
		super(name, displayChar, meleeDamage, "strikes");
		// TODO Auto-generated constructor stub
		setTargetCapability(singleTarget);
		this.range = -1;
		this.hitChance = initHitChance;
		this.shotDamage = shotDamage;
	}
	
	public Gun(String name, char displayChar, int meleeDamage, int shotDamage, boolean singleTarget, int initRange, double initHitChance) {
		super(name, displayChar, meleeDamage, "strikes");
		// TODO Auto-generated constructor stub
		setTargetCapability(singleTarget);
		this.range = initRange;
		this.hitChance = initHitChance;
		this.shotDamage = shotDamage;
	}

	private void setTargetCapability(boolean singleTarget) {
		if (singleTarget) {
			this.addCapability(GunTargetCapability.SINGLE_TARGET);
		}
		else {
			this.addCapability(GunTargetCapability.DIRECTIONAL);
		}
	}
	
	public int getRange() {
		return range;
	}
	
	public double getHitChance() {
		return hitChance;
	}
	
	public int getShotDamage() {
		return shotDamage;
	}
	
	public abstract Action getShootAction(Display display);
}
