package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Special Action for harvesting crops.
 */
public class HarvestAction extends Action {
	Location locationOfCrop;

	/**
	 * Constructor.
	 * 
	 * @param initLocation the location to harvest
	 */
	public HarvestAction(Location initLocation) {
		// TODO Auto-generated constructor stub
		locationOfCrop = initLocation;
	}

	/**
	 * Override method to perform the action.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		locationOfCrop.setGround(new Dirt());
		if (actor instanceof Farmer) {
			dropFoodHarvest(locationOfCrop);
		}
		else {
			addInventoryHarvest(actor);
		}
		
		return menuDescription(actor);
	}

	/**
	 * Override method to print a description.
	 *
	 * @param actor The actor performing the action.
	 * @return a description of what happened that can be displayed to the user.
	 */
	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " harvests a crop.";
	}
	
	/**
	 * Drops the food to the ground when harvested.
	 *
	 * @param locationOfCrop The location to drop the food.
	 */
	private void dropFoodHarvest(Location locationOfCrop) {
		locationOfCrop.addItem(new Food());
	}
	
	/**
	 * Adds the food into the Actor's inventory.
	 *
	 * @param actor The actor performing the action.
	 */
	private void addInventoryHarvest(Actor actor) {
		actor.addItemToInventory(new Food());
	}
}