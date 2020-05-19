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
		List<Item> items = new ArrayList<Item>(map.locationOf(actor).getItems());
		List<Item> inventory = new ArrayList<Item>(actor.getInventory());

		for (Item item: items) {
			if (item instanceof WeaponItem) 
				for(Item item2: inventory) {
					if(item2 instanceof WeaponItem)
						  new DropItemAction(item2).execute(actor, map);
				}
				return new PickUpItemAction(item);
		}
		return null;
	}
}
