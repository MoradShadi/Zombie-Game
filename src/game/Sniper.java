package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;

/**
 * A sniper that can shoot at any enemy on the map with unlimited range. If the player fires without aiming, the sniper
 * deals the base damage and has a 75% hit chance. If the player fires after aiming for one turn, the sniper deals double
 * damage and has a 90% hit chance. If the player fires after aiming for two turns, the sniper instakills the enemy and has 
 * a 100% hit chance. If the player does anything other than aiming at the same enemy, then the aiming count will be reset.
 * 
 * @author User
 *
 */
public class Sniper extends Gun {
	private int aimCount;
	private Actor aimTarget;
	private int resetTracker;
	
	public Sniper() {
		super("sniper", 'R', 25, 30, true, 0.75);
		aimCount = 0;
	}
	
	/**
	 * Called when the player aims at an enemy. If the player aims at a new enemy, then the aim count is set to 1.
	 * If the player aims at the same enemy as the previous round, then the aim count is set to two. If the aim count for
	 * a specific target is 2, then the player cannot aim at the same target again.
	 * 
	 * @param target target to aim at
	 */
	public void aimSniper(Actor target) {
		if ((aimCount == 0) || (aimTarget != target)) {
			this.aimCount = 1;
			this.aimTarget = target;
			this.resetTracker = 0;
		}
		else if (aimCount == 1 && aimTarget == target) {
			this.aimCount += 1;
		}
	}
	
	/**
	 * Called when the player fires at an enemy. If the player fires at an enemy with 0 aim counts, then returns the base shot damage.
	 * If the player fires at an enemy with 1 aim count, then returns double damage and if the player fires at an enemy with 2 aim count,
	 * then returns the max hp of the target to instakill it.
	 * 
	 * @param target target to fire at
	 * @return damage that the shot deals
	 */
	public int getShotDamage(Actor target) {
		if (aimCount == 1 && target == this.aimTarget) {
			return super.getShotDamage() * 2;
		}
		else if (aimCount == 2 && target == this.aimTarget) {
			return target.getMaxHp();
		}
		else {
			return super.getShotDamage();
		}
	}
	
	/**
	 * Called when the player fires at an enemy. If the player fires at an enemy with 0 aim counts, then returns the base 0.75 hit chance.
	 * If the player fires at an enemy with 1 aim count, then returns 0.9 hit chance and if the player fires at an enemy with 2 aim count,
	 * then returns the 1.0 hit chance so the bullet will not miss.
	 * 
	 * @param target target to fire at
	 * @return hit chance of the shot
	 */
	public double getHitChance(Actor target) {
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
	
	/**
	 * Keeps track of the aim concentration of the player. If the player aimed in the previous turn but does another action in the current
	 * turn, then the aim count will not be increased but the reset tracker will be increased by 1, so we will know that the concentration
	 * has to be reset.
	 * 
	 */
	public void tick(Location currentLocation, Actor actor) {
		if (this.aimCount > 0) {
			resetTracker += 1;
			if (resetTracker > aimCount) {
				this.resetAim();
			}
		}
	}
	
	/**
	 * Resets the aim concentration of the player
	 * 
	 */
	public void resetAim() {
		resetTracker = 0;
		aimCount = 0;
		aimTarget = null;
	}

	public int getAimCount() {
		return aimCount;
	}
	
	public Actor getTarget() {
		return aimTarget;
	}

}
