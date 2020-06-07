package game;

import edu.monash.fit2099.engine.WeaponItem;

public abstract class Gun extends WeaponItem {
	private int range;
	private double hitChance;
	private int shotDamage;

	public Gun(String name, char displayChar, int meleeDamage, int shotDamage, boolean singleTarget, double initHitChance) {
		super(name, displayChar, meleeDamage, "strikes");
		// TODO Auto-generated constructor stub
		setTargetCapability(singleTarget);
		setHitChance(initHitChance);
		setShotDamage(shotDamage);
	}
	
	public Gun(String name, char displayChar, int meleeDamage, int shotDamage, boolean singleTarget, double initHitChance, int initRange) {
		super(name, displayChar, meleeDamage, "strikes");
		// TODO Auto-generated constructor stub
		setTargetCapability(singleTarget);
		this.range = initRange;
		setHitChance(initHitChance);
		setShotDamage(shotDamage);
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
	
	public void setHitChance(double newHitChance) {
		if (newHitChance <= 100 && newHitChance >= 0) {
			this.hitChance = newHitChance;
		}
	}
	
	public int getShotDamage() {
		return shotDamage;
	}
	
	public void setShotDamage(int newDamage) {
		if (newDamage > 0) {
			this.shotDamage = newDamage;
		}
	}
	
}
