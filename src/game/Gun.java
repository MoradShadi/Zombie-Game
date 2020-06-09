package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * Base class for guns that can only be fired if it has ammo
 * 
 * @author User
 *
 */
public abstract class Gun extends WeaponItem {
	private int range;
	private double hitChance;
	private int shotDamage;
	private int ammoCount;

	/**
	 * Constructor for gun with unlimited range
	 * 
	 * @param name name of the gun
	 * @param displayChar display character on console
	 * @param meleeDamage damage dealt if the gun is used for a melee attack on an adjacent Actor
	 * @param shotDamage damage dealt if the gun is shot at another Actor
	 * @param singleTarget whether the gun is a single target gun
	 * @param initHitChance chance that the gunshot hits the Actor
	 */
	public Gun(String name, char displayChar, int meleeDamage, int shotDamage, boolean singleTarget, double initHitChance) {
		super(name, displayChar, meleeDamage, "strikes");
		// TODO Auto-generated constructor stub
		setTargetCapability(singleTarget);
		setHitChance(initHitChance);
		setShotDamage(shotDamage);
		this.ammoCount = 0;
	}
	
	/**
	 * Constructor for gun with limited range
	 * 
	 * @param name name of the gun
	 * @param displayChar display character on console
	 * @param meleeDamage dealt if the gun is used for a melee attack on an adjacent Actor
	 * @param shotDamage damage dealt if the gun is shot at another Actor
	 * @param singleTarget whether the gun is a single target gun
	 * @param initHitChance chance that the gunshot hits the Actor
	 * @param initRange range limit of the gun
	 */
	public Gun(String name, char displayChar, int meleeDamage, int shotDamage, boolean singleTarget, double initHitChance, int initRange) {
		super(name, displayChar, meleeDamage, "strikes");
		// TODO Auto-generated constructor stub
		setTargetCapability(singleTarget);
		this.range = initRange;
		setHitChance(initHitChance);
		setShotDamage(shotDamage);
		this.ammoCount = 0;
	}

	/**
	 * Modifier for the targeting system of the gun
	 * 
	 * @param singleTarget whether the gun is a single target gun
	 */
	private void setTargetCapability(boolean singleTarget) {
		if (singleTarget) {
			this.addCapability(GunTargetCapability.SINGLE_TARGET);
		}
		else {
			this.addCapability(GunTargetCapability.DIRECTIONAL);
		}
	}
	
	/**
	 * Gets the range limit for the gun
	 * 
	 * @return gun range limit
	 */
	public int getRange() {
		return range;
	}
	
	/**
	 * Gets the chance that the gunshot hits the target
	 * 
	 * @return hit chance
	 */
	public double getHitChance() {
		return hitChance;
	}
	
	/**
	 * Modifier for the hit chance of the gun
	 * 
	 * @param newHitChance new hit chance of the gun
	 */
	public void setHitChance(double newHitChance) {
		if (newHitChance <= 100 && newHitChance >= 0) {
			this.hitChance = newHitChance;
		}
	}
	
	/**
	 * Accessor for the shooting damage of the gun
	 * 
	 * @return gun shooting damage
	 */
	public int getShotDamage() {
		return shotDamage;
	}
	
	/**
	 * Modifier for the shooting damage of the gun
	 * 
	 * @param newDamage new shooting damage
	 */
	public void setShotDamage(int newDamage) {
		if (newDamage > 0) {
			this.shotDamage = newDamage;
		}
	}
	
	/**
	 * Reloads the gun with more ammo
	 * 
	 * @param ammo amount of ammo to increase
	 */
	public void reload(int ammo) {
		this.ammoCount += ammo;
	}
	
	public int getAmmoCount() {
		return this.ammoCount;
	}
	
	/**
	 * Gets the action to display a submenu so the player can further choose the gun options
	 * 
	 * @param display display where the menu will be shown
	 * @return submenu display action
	 */
	public Action getShootingAction(Display display) {
		if (this.getAmmoCount() > 0) {
			return new ShootingSubmenu(this, display);
		}
		return null;
	}
}
