package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * Returns an action that will make actor pick up food on the ground
 * 
 * @author User
 *
 */
public class PickUpFoodBehaviour implements Behaviour {

	public PickUpFoodBehaviour() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action getAction(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		List<Item> groundItems = map.locationOf(actor).getItems();
		for (Item item : groundItems) {
			if (item instanceof Food) {
				return new PickUpItemAction(item);
			}
		}
		return null;
	}

}
