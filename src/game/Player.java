package game;

import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Menu;
import edu.monash.fit2099.engine.Weapon;

/**
 * Class representing the Player.
 */
public class Player extends Human {
	private Random rand = new Random();

	private Menu menu = new Menu();
	
	private Location[][] map;

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
	 * @return the Actor's weapon or natural attack
	 */
	@Override
	public Weapon getWeapon() {
		//We override the getWeapon method for all characters so that there is more adjustability for the miss rate for each character
		//and we can encapsulate the miss rate for each character in their own class
		if (rand.nextBoolean()) {
			//base 50% chance to miss attack
			return null;
		}
		return super.getWeapon();
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		List<Item> items = this.getInventory();
		for (Item item: items) {
			if (item.hasCapability(CraftableWeaponCapability.CRAFTABLE))
				actions.add(new CraftWeaponAction(item));
		}
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		return menu.showMenu(this, actions, display);
	}
	
	public void harvestCrop(int x, int y, GameMap gameMap, Player player) {
		// location for left
		int x_left = x - 1;
		int x_right = x + 1;
		Harvest harvest = new Harvest();	
		
		Ground ground_l = map[x_left][y].getGround();
		Ground ground_r = map[x_right][y].getGround();
			
		if(ground_l.getDisplayChar() == '^') {
			Crop crop = (Crop) ground_l;
			harvest.playerHarvest(player, crop, gameMap);
		}
		
		if(ground_r.getDisplayChar() == '^') {
			Crop crop = (Crop) ground_r;
			harvest.playerHarvest(player, crop, gameMap);	
		}
		
		Ground ground = map[x][y].getGround();
		
		if (ground.getDisplayChar() == '^') {
			Crop crop = (Crop) ground;
			harvest.playerHarvest(player, crop, gameMap);
		}
		
	}
	
	public void eatFood(Player player) {
		inventory = player.getInventory();
		for(Item item: inventory) {
			if (item.getDisplayChar() == 'o') {
				player.eatFood(player);;
			}
		}
	}
}
