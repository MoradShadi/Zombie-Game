package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.PickUpItemAction;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * Returns an action that will make actor pick up weapon from the ground
 * @author User
 *
 */
public class PickUpWeaponBehaviour implements Behaviour {
	private Random rand = new Random();

	/**
	 * Check if there is a weapon on the ground, and return a PickUpItemAction for the first weapon on the ground. 
	 * If the actor is already holding a weapon, then it will throw that weapon onto an adjacent location and swap
	 * for the new weapon on the ground below.
	 *
	 * @param  actor The actor performing the action.
	 * @param  map   The map the actor is on.
	 * @return the FertilizeAction to be performed.
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		List<Item> items = map.locationOf(actor).getItems();
		List<Item> inventory = actor.getInventory();

		for (Item groundItem: items) {
			if (groundItem instanceof WeaponItem) 
				for(Item inventoryItem: inventory) {
					if(inventoryItem instanceof WeaponItem) {
						dropWeaponOnRandomLocation(actor, map, inventoryItem);
						actor.removeItemFromInventory(inventoryItem);
					}
				}
				return new PickUpItemAction(groundItem);
		}
		
		return null;
	}
	
	/**
	 * Drop the weapon on an adjacent location beside the actor
	 * 
	 * @param actor the actor whose weapon is dropped
	 * @param map the map the actor is on
	 * @param weapon the weapon that is dropped.
	 */
	private void dropWeaponOnRandomLocation(Actor actor, GameMap map, Item weapon) {
		Location actorLocation = map.locationOf(actor);
		List<Exit> allAdjacentLocations = actorLocation.getExits();
		ArrayList<Location> validAdjacentLocations = new ArrayList<Location>();
		
		for (Exit exit : allAdjacentLocations) {
			Location adjacentLocation = exit.getDestination();
			boolean walkableGround = adjacentLocation.getGround().canActorEnter(actor);
			if (walkableGround) {
				//The adjacent location is valid if Actors can walk on it so that another actor can pick up that weapon by standing on it
				validAdjacentLocations.add(adjacentLocation);
			}
		}
		
		int randomIndex = rand.nextInt(validAdjacentLocations.size());
		//Drop the limb on a random valid location beside the zombie
		validAdjacentLocations.get(randomIndex).addItem(weapon);
	}
}
