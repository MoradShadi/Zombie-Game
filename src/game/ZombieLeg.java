package game;

import edu.monash.fit2099.engine.Item;

public class ZombieLeg extends ZombieLimb {

	public ZombieLeg() {
		super("Leg", '[');
		// TODO Auto-generated constructor stub
	}
	
	public Item getCraftedWeapon() {
		return new ZombieMace();
	}
}
