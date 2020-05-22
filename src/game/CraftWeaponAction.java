package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * Special action for crafting weapons
 * 
 * @author User
 *
 */
public class CraftWeaponAction extends Action {
	private Item oldWeapon;
	
	/**
	 * Constructor
	 * 
	 * @param weapon the weapon that will be crafted
	 */
	public CraftWeaponAction(Item weapon){
			this.oldWeapon = weapon;
	}
	
	/**
	 * Perform the crafting action
	 * 
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 */
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
