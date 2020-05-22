package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;

/**
 * Returns a FertilizeAction that will make the Actor fertilize a crop.
 */
public class FertilizeBehaviour implements Behaviour {
	
	/**
	 * Constructor.
	 * 
	 */
	public FertilizeBehaviour() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Check if there is an unripe crop at the actor's location and return a fertilize action if there is.
	 *
	 * @param  actor The actor performing the action.
	 * @param  map   The map the actor is on.
	 * @return the FertilizeAction to be performed.
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		Ground farmerGround = map.locationOf(actor).getGround();
		if (farmerGround.getDisplayChar() == '^') {
			Crop crop = (Crop) farmerGround;
			if (!crop.isRipe()) {
				return new FertilizeAction(crop);
			}
		}
		
		return null;
	}

}
