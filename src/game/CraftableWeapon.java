package game;

import edu.monash.fit2099.engine.Item;

/**
 * A CrafatbleWeapon represents a WeaponItem that can be crafted into a better weapon
 * 
 * @author User
 *
 */
public interface CraftableWeapon{
	/**
	 * A craftable weapon must implement this method to get its upgraded version.
	 * 
	 * @return a new instance of the crafted weapon.
	 */
	Item getCraftedWeapon();
}
