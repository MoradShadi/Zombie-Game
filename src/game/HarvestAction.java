package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class HarvestAction extends Action {
	Location locationOfCrop;

	public HarvestAction(Location initLocation) {
		// TODO Auto-generated constructor stub
		locationOfCrop = initLocation;
	}

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

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " harvested a crop.";
	}
	
	private void dropFoodHarvest(Location locationOfCrop) {
		locationOfCrop.addItem(new Food());
	}

	private void addInventoryHarvest(Actor actor) {
		actor.addItemToInventory(new Food());
	}
}
