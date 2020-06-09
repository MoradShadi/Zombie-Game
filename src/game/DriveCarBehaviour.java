package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class DriveCarBehaviour implements Behaviour {
	
	//Constructor
	public DriveCarBehaviour() {
		// TODO Auto-generated constructor stub
	}

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
