package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
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
		
		if (weapon == null) {
			//if getWeapon returns null, it means the actor has missed his attack.
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

		if (target instanceof Zombie) {
			Zombie targetZombie = (Zombie) target;
			ArrayList<ZombieLimb> droppedZombieLimbs = targetZombie.zombieHurt(damage);
			if (droppedZombieLimbs != null) {
				dropLimbOnMap(targetZombie, droppedZombieLimbs, map);
			}	
		}
		else {
			target.hurt(damage);
		}
		
		if (!target.isConscious()) {
			if (target instanceof Human) {
				Corpse humanCorpse = new Corpse(target + "'s corpse");
				map.locationOf(target).addItem(humanCorpse);
			}
			else {
				Item corpse = new PortableItem("dead " + target, '%');
				map.locationOf(target).addItem(corpse);
				
			}
			
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
	
	private void dropLimbOnMap(Zombie targetZombie, ArrayList<ZombieLimb> lostLimbs, GameMap map) {
		Location zombieLocation = map.locationOf(targetZombie);
		List<Exit> allAdjacentLocations = zombieLocation.getExits();
		ArrayList<Location> validAdjacentLocations = new ArrayList<Location>();
		
		for (Exit exit : allAdjacentLocations) {
			Location adjacentLocation = exit.getDestination();
			boolean walkableGround = adjacentLocation.getGround().canActorEnter(targetZombie);
			if (walkableGround) {
				validAdjacentLocations.add(adjacentLocation);
			}
		}
		
		int numOfValidGrounds = validAdjacentLocations.size();
		for (ZombieLimb droppedLimb : lostLimbs) {
			int randomIndex = rand.nextInt(numOfValidGrounds);
			validAdjacentLocations.get(randomIndex).addItem(droppedLimb);
		}
	}
}
