package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target) {
		this.target = target;
	}

	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

		if (rand.nextBoolean()) { 
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

		target.hurt(damage);
		
		double rollLoseLimb = rand.nextDouble();
		if (target.hasCapability(ZombieCapability.UNDEAD) && rollLoseLimb <= 0.25) {
			Zombie targetZombie = (Zombie) target;
			boolean loseArm = false;
			boolean loseLeg = false;
			
			if (targetZombie.hasArm() && targetZombie.hasLeg()) {
				boolean rollArmOrLeg = rand.nextBoolean();
				double rollArmAndLeg;
				
				if (rollArmOrLeg) {
					loseArm = true;
					rollArmAndLeg = rand.nextDouble();
					if (rollArmAndLeg <= 0.25) { //25% chance to lose leg also 
						loseLeg = true;
					}
				}
				else {
					loseLeg = true;
					rollArmAndLeg = rand.nextDouble();
					if (rollArmAndLeg <= 0.25) { //25% chance to lose arm also
						loseArm = true;
					}
				}
				result += loseLimb(targetZombie, loseArm, loseLeg);
			}
			else if (targetZombie.hasArm()) {
				loseArm = true;
				result += loseLimb(targetZombie, loseArm, loseLeg);
			}
			else if (targetZombie.hasLeg()) {
				loseLeg = true;
				result += loseLimb(targetZombie, loseArm, loseLeg);
			}
		}
		
		if (!target.isConscious()) {
			Item corpse = new PortableItem("dead " + target, '%');
			map.locationOf(target).addItem(corpse);
			
			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);	
			
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}
	
	private String loseLimb(Zombie targetZombie, boolean loseArm, boolean loseLeg) {
		String outputStr = "";
		
		if (targetZombie.hasArm() && loseArm) {
			double rollLoseTwoArms = rand.nextDouble(); //25% chance to lose 2 arms instead of one
			if (rollLoseTwoArms <= 0.25 && targetZombie.armCount() == 2) {
				targetZombie.removeArm(2);
				outputStr += System.lineSeparator() + targetZombie + " lost 2 arms.";
			}
			else {
				targetZombie.removeArm(1);
				outputStr += System.lineSeparator() + targetZombie + " lost 1 arm.";
			}
		}
		
		if (targetZombie.hasLeg() && loseLeg) {
			double rollLoseTwoLegs = rand.nextDouble(); //25% chance to lose 2 legs instead of one
			if (rollLoseTwoLegs <= 0.25 && targetZombie.legCount() == 2) {
				targetZombie.removeLeg(2);
				outputStr += System.lineSeparator() + targetZombie + " lost 2 legs.";
			}
			else {
				targetZombie.removeLeg(1);
				outputStr += System.lineSeparator() + targetZombie + " lost 1 leg.";
			}
		}
		
		return outputStr;
	}
}
