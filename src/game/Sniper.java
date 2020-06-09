package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;

public class Sniper extends Gun {
	private int aimCount;
	private Zombie aimTarget;
	private int resetTracker;
	
	public Sniper() {
		super("sniper", 'R', 25, 30, true, 0.75);
		aimCount = 0;
	}
	
	public void aimSniper(Zombie target) {
		if ((aimCount == 0) || (aimTarget != target)) {
			this.aimCount = 1;
			this.aimTarget = target;
			this.resetTracker = 0;
		}
		else if (aimCount == 1 && aimTarget == target) {
			this.aimCount += 1;
		}
	}
	
	public int getShotDamage(Zombie target) {
		if (aimCount == 1 && target == this.aimTarget) {
			return super.getShotDamage() * 2;
		}
		else if (aimCount == 2 && target == this.aimTarget) {
			return target.getMaxHP();
		}
		else {
			return super.getShotDamage();
		}
	}
	
	public double getHitChance(Zombie target) {
		if (aimCount == 1 && target == this.aimTarget) {
			return 0.9;
		}
		else if (aimCount == 2 && target == this.aimTarget) {
			return 1;
		}
		else {
			return super.getHitChance();
		}
	}
	
	public void tick(Location currentLocation, Actor actor) {
		if (this.aimCount > 0) {
			resetTracker += 1;
			if (resetTracker > aimCount) {
				this.resetAim();
			}
		}
	}
	
	public void resetAim() {
		resetTracker = 0;
		aimCount = 0;
		aimTarget = null;
		System.out.println("AAAAAAAAAAA");
	}

	public int getAimCount() {
		return aimCount;
	}
	
	public Zombie getTarget() {
		return aimTarget;
	}

}
