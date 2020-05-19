package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class CraftWeaponAction extends Action {
	protected Item item;
	
	public CraftWeaponAction(ZombieLimb limb){
			item = limb;
	}
	
	
	@Override
	public String execute(Actor actor, GameMap map) {
		if (item instanceof ZombieLeg) {
			actor.removeItemFromInventory(item);
			ZombieMace mace = new ZombieMace();
			item = mace;

		}
		else if (item instanceof ZombieArm){
			actor.removeItemFromInventory(item);
			ZombieClub club = new ZombieClub();
			item = club;
		}
		actor.addItemToInventory(item);		
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + "crafts a " + item;
	}

}
