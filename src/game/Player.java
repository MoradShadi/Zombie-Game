package game;

import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Menu;
import edu.monash.fit2099.engine.Weapon;

/**
 * Class representing the Player.
 */
public class Player extends Human {
	private Random rand = new Random();

	private Menu menu = new Menu();
	
	private Behaviour[] behaviours = {
			new EatFoodBehaviour(),
			new HarvestBehaviour(),
			new CutTreeBehaviour()
	};
	

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}
	
	/**
	 * Get the weapon this Player is using and determine if the Player misses the attack.
	 * 
	 * If the attack is approved and if the current Player is carrying weapons, 
	 * returns the first one in the inventory. Otherwise, returns the Player's 
	 * natural fighting equipment e.g. fists.
	 *
	 * @return the Actor's weapon or natural
	 */
	@Override
	public Weapon getWeapon() {
		if (rand.nextBoolean()) {
			//base 50% chance to miss attack
			return null;
		}
		return super.getWeapon();
	}
	
	@Override
	public void hurt(int points) {
		super.hurt(points);
		List<Item> items = this.getInventory();	
		for (Item item: items) {
			if (item.hasCapability(GunTargetCapability.SINGLE_TARGET)) {
				Sniper sniper = (Sniper) item;
				sniper.resetAim();
			}
		}
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		
		//Check if any of the inventory weapons is craftable or is a gun and add the craft actions or shooting action to the Actions list.
		List<Item> items = this.getInventory();	
		for (Item item: items) {
			if (item.hasCapability(CraftableWeaponCapability.CRAFTABLE)) {
				actions.add(new CraftWeaponAction(item));
			}
			
			boolean itemIsGun = item.hasCapability(GunTargetCapability.DIRECTIONAL) || item.hasCapability(GunTargetCapability.SINGLE_TARGET);
			if (itemIsGun) {
				Gun gun = (Gun) item;
				Action shootingAction = gun.getShootingAction(display);
				if (shootingAction != null) {
					actions.add(shootingAction);
				}
			}
		}
		
		//Get eat food and harvest crop actions
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null) {
				actions.add(action);
			}
		}
		
		actions.add(new ExitAction());
		
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		
		return menu.showMenu(this, actions, display);
	}
}
