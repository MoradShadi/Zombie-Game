package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * Returns a SowCropAction that will make the Actor sows a crop.
 */
public class SowCropBehaviour implements Behaviour {
	double sowCropChance;

	private Random rand = new Random();
	
	/**
	 * Constructor.
	 * 
	 */
	public SowCropBehaviour() {
		sowCropChance = 0.33;
	}

	/**
	 * Override method to get the action.
	 *
	 * @param  actor The actor performing the action.
	 * @param  gameMap   The map the actor is on.
	 * @return the SowCropAction to be performed.
	 */
	@Override
	public Action getAction(Actor actor, GameMap gameMap) {
		// TODO Auto-generated method stub
		Location farmerLocation = gameMap.locationOf(actor);
		List<Exit> allAdjacentLocations = farmerLocation.getExits();
		ArrayList<Location> validAdjacentLocations = new ArrayList<Location>();
		
		for (Exit exit : allAdjacentLocations) {
			Location adjacentLocation = exit.getDestination();
			Ground adjacentGround = adjacentLocation.getGround();
			if (adjacentGround.getDisplayChar() == '.') {
				validAdjacentLocations.add(adjacentLocation);
			}
		}
		
		boolean sowCropApproved = rand.nextDouble() < sowCropChance;
		if (sowCropApproved && !validAdjacentLocations.isEmpty()) { //33% chance to sow crop and there must be at least one Dirt ground beside Farmer
			int randomLocation = rand.nextInt(validAdjacentLocations.size());
			Location chosenLocation = validAdjacentLocations.get(randomLocation);
			
			return new SowCropAction(chosenLocation);
		}
		
		return null;
	}
}
