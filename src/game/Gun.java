package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.WeaponItem;

public abstract class Gun extends WeaponItem {
	private int range;

	public Gun(String name, char displayChar, int damage, boolean singleTarget) {
		super(name, displayChar, damage, "strikes");
		// TODO Auto-generated constructor stub
		setTargetCapability(singleTarget);
		this.range = -1;
	}
	
	public Gun(String name, char displayChar, int damage, boolean singleTarget, int initRange) {
		super(name, displayChar, damage, "strikes");
		// TODO Auto-generated constructor stub
		setTargetCapability(singleTarget);
		this.range = initRange;
	}

	private void setTargetCapability(boolean singleTarget) {
		if (singleTarget) {
			this.addCapability(GunTargetCapability.SINGLE_TARGET);
		}
		else {
			this.addCapability(GunTargetCapability.DIRECTIONAL);
		}
	}
	
	public abstract Action getShootAction();
}
