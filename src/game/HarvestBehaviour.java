package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * Returns a HarvestAction that will make the Actor harvest a crop for food.
 */
public class HarvestBehaviour implements Behaviour {
	private Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 */
	public HarvestBehaviour() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Overload getAction. If a normal Actor gets action, then it will check if there is any adjacent ripe crops to harvest
	 * and choose a random ripe crop and return a harvest action for that chosen crop.
	 *
	 * @param  actor The actor performing the action.
	 * @param  map   The map the actor is on.
	 * @return the HarvestAction to be performed.
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		ArrayList<Location> ripeCrops = findRipeCrops(actor, map);
		
		//choose a random ripe crop for normal actors to harvest if there are any
		if (!ripeCrops.isEmpty()) {
			int randomCrop = rand.nextInt(ripeCrops.size());
			return new HarvestAction(ripeCrops.get(randomCrop));
		}
		
		return null;
	}
	
	/**
	 * Overload getAction. If a Player gets action, then it will check for all adjacent ripe crops to harvest and
	 * return the harvest actions for all the crops, so the PLayer can choose which crop to harvest.
	 *
	 * @param  player The player performing the action.
	 * @param  map    The map the player is on.
	 * @return the HarvestAction to be performed.
	 */
	public Actions getAction(Player player, GameMap map) {
		Actor playerAsActor = (Actor) player;
		Actions harvestActions = new Actions();
		ArrayList<Location> ripeCrops = findRipeCrops(playerAsActor, map);
		
		//return all possible harvest actions for player
		if (!ripeCrops.isEmpty()) {
			for (Location location : ripeCrops) {
				harvestActions.add(new HarvestAction(location));
			}
			return harvestActions;
		}
		
		return null;
	}

	/**
	 * Checks if a crop is ripe.
	 *
	 * @param  location Location of the crop.
	 * @return true if the crop is ripe or false otherwise.
	 */
	private boolean isRipeCrop(Location location) {
		Ground ground = location.getGround();
		if(ground instanceof Crop) {
			Crop crop = (Crop) ground;
			if (crop.isRipe()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Finds ripe crops.
	 *
	 * @param  actor The actor performing the action.
	 * @param  map   The map the actor is on.
	 * @return all ripe crops adjacent to actor or below actor.
	 */
	private ArrayList<Location> findRipeCrops(Actor actor, GameMap map) {
		Location actorLocation = map.locationOf(actor);
		List<Exit> allAdjacentLocations = actorLocation.getExits();
		ArrayList<Location> ripeCrops = new ArrayList<Location>();
		
		//if actor is stepping on ripe crop
		if (isRipeCrop(actorLocation)) {
			ripeCrops.add(actorLocation);
		}
		
		//find all ripe crops that is adjacent to actor
		for (Exit exit : allAdjacentLocations) {
			Location adjacentLocation = exit.getDestination();
			if (isRipeCrop(adjacentLocation)) {
				ripeCrops.add(adjacentLocation);
			}
		}
		
		return ripeCrops;
	}
}
