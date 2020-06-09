package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * A class that generates an DriveCarAction if the current Actor choose to drive to another destination.
 *
 */
public class DriveCarBehaviour implements Behaviour {
	
	/**
	 * Constructor.
	 */
	public DriveCarBehaviour() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns an DriveCarAction that allows Actor to drive to another location.
	 * 
	 * @param actor The actor driving the car
	 * @param map The map the actor is on
	 * @return the DriveCarAction that allows Actor to drive to another location
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		List<Item> groundItems = map.locationOf(actor).getItems();
		for (Item item : groundItems) {
			if (item instanceof Car) {
				Car car = (Car) item;
				return new DriveCarAction(car.getDestination(), car.getDestinationMsg());
			}
		}
		return null;
	}

}
