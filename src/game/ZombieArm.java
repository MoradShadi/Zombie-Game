package game;

import edu.monash.fit2099.engine.Item;

/**
 * A simple club weapon that doubles as the zombie's arm
 * 
 * @author User
 *
 */
public class ZombieArm extends ZombieLimb {

	public ZombieArm() {
		super("arm", '~');
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get the upgraded verson of this weapon
	 */
	public Item getCraftedWeapon() {
		return new ZombieClub();
	}
}
