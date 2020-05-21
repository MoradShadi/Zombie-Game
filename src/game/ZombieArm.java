package game;

import edu.monash.fit2099.engine.Item;

public class ZombieArm extends ZombieLimb {

	public ZombieArm() {
		super("arm", '~');
		// TODO Auto-generated constructor stub
	}
	
	public Item getCraftedWeapon() {
		return new ZombieClub();
	}
}
