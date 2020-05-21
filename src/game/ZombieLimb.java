package game;

import edu.monash.fit2099.engine.WeaponItem;
import edu.monash.fit2099.interfaces.CraftableWeapon;

public abstract class ZombieLimb extends WeaponItem implements CraftableWeapon{

	public ZombieLimb(String name, char displayChar) {
		super(name, displayChar, 15, "clubs");
		// TODO Auto-generated constructor stub
		this.addCapability(CraftableWeaponCapability.CRAFTABLE);
	}
}
