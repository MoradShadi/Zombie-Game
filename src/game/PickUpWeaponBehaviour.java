package game;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DropItemAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;
import edu.monash.fit2099.engine.WeaponItem;


public class PickUpWeaponBehaviour implements Behaviour {

	
	@Override
	public Action getAction(Actor actor, GameMap map) {
		Zombie zombie = (Zombie) actor;
		ArrayList<Item> items = new ArrayList<Item>(map.locationOf(zombie).getItems());
		ArrayList<Item> inventory = new ArrayList<Item>(zombie.getInventory());

		if (zombie.hasArm()) {
			for (Item groundItem: items) {
				if (groundItem instanceof WeaponItem) 
					for(Item inventoryItem: inventory) {
						if(inventoryItem instanceof WeaponItem)
							  new DropItemAction(inventoryItem).execute(zombie, map);
					}
					return new PickUpItemAction(groundItem);
			}
		}
		return null;
	}
}
