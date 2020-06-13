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

	/**
	 * Perform the attacking Action on the target and determines if the attack misses or hits
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();
		
		if (weapon == null) {
			//if getWeapon returns null, it means the actor has missed his attack.
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		
		this.hurtTarget(target, damage, map);
		
		result += this.checkAlive(target, map);

		return result;
	}
	
	/**
	 * Special execute for gun shot attacks and calculates if the shot will hit the target
	 * 
	 * @param actor the shooter
	 * @param map the map that the shooter is on
	 * @param gunDmg the damage of the shot
	 * @param hitChance the chance that the shot hits the target
	 * @return
	 */
	public String gunShotExecute(Actor actor, GameMap map, int gunDmg, double hitChance) {
		if (gunDmg < 0 || hitChance < 0) {
			throw new IllegalArgumentException("Damage or hit chance cannot be negative");
		}
		boolean shotHit = rand.nextDouble() <= hitChance;
		String result = "";
		
		if (shotHit) {
			result = actor + " shoots " + target + " for " + gunDmg + " damage.";
			
			this.hurtTarget(target, gunDmg, map);
			
			result += this.checkAlive(target, map);
		}
		else {
			result = "The shot misses " + target + ".";
		}
		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}
	
	/**
	 * Method to hurt the target according to what target it is
	 * If the atack target is a human, hurt the target
	 * If the attack target is a zombie, hurt the target then check if the zombie dropped any limbs or weapons and drop them on the map accordingly.
	 * 
	 * @param target target that will be hurt
	 * @param damage damage dealt
	 * @param map map that the target is on
	 */
	private void hurtTarget(Actor target, int damage, GameMap map) {
		if (target instanceof Zombie) {
			Zombie targetZombie = (Zombie) target;
			ArrayList<Item> droppedZombieLimbsAndWeapons = targetZombie.zombieHurt(damage);
			if (droppedZombieLimbsAndWeapons != null) {
				dropItemOnMap(targetZombie , droppedZombieLimbsAndWeapons, map);
			}	
		}
		
		else {
			target.hurt(damage);
		}
	}
	
	/**
	 * Method to check if the target is dead after the attack and handles the dying procedures according to the target class
	 * If a human is attacked and it dies, then turns the human into a Corpse that will turn into a zombie after some turns.
	 * If a zombie is attacked and dies, create a normal corpse.
	 * 
	 * @param target target to check
	 * @param map map that the target is on
	 * @return console message if the actor dies
	 */
	private String checkAlive(Actor target, GameMap map) {
		String result = "";
		if (!target.isConscious()) {
			if (target.hasCapability(ZombieCapability.ALIVE)) {
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
			
			result = System.lineSeparator() + target + " is killed.";
		}
		return result;
	}
	
	/**
	 * Place the limbs and weapon that is dropped when the the zombie is attacked onto random adjacent locations
	 * beside the zombie, so that other actors have a chance to pick it up too.
	 * 
	 * @param targetZombie the zombie that lost its limbs and weapon
	 * @param droppedLimbsAndWeapons the limbs and weapons that are dropped
	 * @param map the map that the zombie is on
	 */
	private void dropItemOnMap(Zombie targetZombie, ArrayList<Item> droppedLimbsAndWeapons, GameMap map) {
		Location zombieLocation = map.locationOf(targetZombie);
		List<Exit> allAdjacentLocations = zombieLocation.getExits();
		ArrayList<Location> validAdjacentLocations = new ArrayList<Location>();
		
		//Get all the valid adjacent locations to put an item
		for (Exit exit : allAdjacentLocations) {
			Location adjacentLocation = exit.getDestination();
			boolean walkableGround = adjacentLocation.getGround().canActorEnter(targetZombie);
			if (walkableGround) {
				//The adjacent location is valid if Actors can walk on it so that they can pick up the limb as weapon
				validAdjacentLocations.add(adjacentLocation);
			}
		}
		
		//Add all the items onto a random valid location
		for (Item droppedItem : droppedLimbsAndWeapons) {
			int randomIndex = rand.nextInt(validAdjacentLocations.size());
			//Drop the limb on a random valid location beside the zombie
			validAdjacentLocations.get(randomIndex).addItem(droppedItem);
		}
	}
}
