package game;

import edu.monash.fit2099.engine.Item;

/**
 * A simple club weapon that doubles as the zombie's leg
 * @author User
 *
 */
public class ZombieLeg extends ZombieLimb {

	public ZombieLeg() {
		super("leg", '[');
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get the upgraded version of this weapon
	 */
	public Item getCraftedWeapon() {
		return new ZombieMace();
	}
}
