package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;


public class CraftWeaponAction extends Action {
	protected Item oldWeapon;
	
	public CraftWeaponAction(Item weapon){
			this.oldWeapon = weapon;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		CraftableWeapon weaponAsCraftble = (CraftableWeapon) oldWeapon;
		Item upgradedWeapon = weaponAsCraftble.getCraftedWeapon();
		actor.removeItemFromInventory(oldWeapon);
		actor.addItemToInventory(upgradedWeapon);		
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " crafts " + oldWeapon;
	}

}
