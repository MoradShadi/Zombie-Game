package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.WeaponItem;

public abstract class Gun extends WeaponItem {
	private int range;
	private double hitChance;
	private int shotDamage;
	private int ammoCount;

	public Gun(String name, char displayChar, int meleeDamage, int shotDamage, boolean singleTarget, double initHitChance) {
		super(name, displayChar, meleeDamage, "strikes");
		// TODO Auto-generated constructor stub
		setTargetCapability(singleTarget);
		setHitChance(initHitChance);
		setShotDamage(shotDamage);
		this.ammoCount = 0;
	}
	
	public Gun(String name, char displayChar, int meleeDamage, int shotDamage, boolean singleTarget, double initHitChance, int initRange) {
		super(name, displayChar, meleeDamage, "strikes");
		// TODO Auto-generated constructor stub
		setTargetCapability(singleTarget);
		this.range = initRange;
		setHitChance(initHitChance);
		setShotDamage(shotDamage);
		this.ammoCount = 0;
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
	
	public void reload(int ammo) {
		this.ammoCount += ammo;
	}
	
	public int getAmmoCount() {
		return this.ammoCount;
	}
	
	public Action getShootingAction(Display display) {
		if (this.getAmmoCount() > 0) {
			return new ShootingSubmenu(this, display);
		}
		return null;
	}
}
