package game;

import java.util.ArrayList;
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
	MamboMarie voodooPriestess = new MamboMarie("Vodoo Priestess");
	
	private Random rand = new Random();

	private Menu menu = new Menu();
	
	private Behaviour[] behaviours = {
			new EatFoodBehaviour(),
			new HarvestBehaviour(),
			new ExitBehaviour()
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
		// Reload guns at the start of every turn
		this.reloadAllGuns();
		// Handle multi-turn Actions
		
		if (!voodooPriestess.getOnMap() && !(voodooPriestess == null)) {
			if (rand.nextDouble() <= 0.05) {
				double edgeToSpawn = rand.nextDouble();
				int xMax = map.getXRange().max();
				int yMax = map.getYRange().max();
				int xCoordinate;
				int yCoordinate;
				
				// each edge has an equal 25% chance of spawning mambo marie
				if (edgeToSpawn < 0.25) { // spawn on left edge (edgeToSpawn is between 0 and 0.25)
					xCoordinate = 0;
					yCoordinate = rand.nextInt(yMax + 1);
				}
				else if (edgeToSpawn < 0.5) { // spawn on right edge (edgeToSpawn is between 0.25 and 0.5)
					xCoordinate = xMax;
					yCoordinate = rand.nextInt(yMax + 1);
				}
				else if (edgeToSpawn < 0.75) { // spawn on top edge (edgeToSpawn is between 0.5 and 0.75)
					xCoordinate = rand.nextInt(xMax + 1);
					yCoordinate = 0;
				}
				else { // spawn on bottom edge (edgeToSpawn is between 0.75 and 1)
					xCoordinate = rand.nextInt(xMax + 1);
					yCoordinate = yMax;
				}
				
				map.at(xCoordinate, yCoordinate).addActor(voodooPriestess);
				this.voodooPriestess.setOnMap(true);
			}
		}
		
		if (!voodooPriestess.isConscious()) {
			voodooPriestess = null;
		}
		
		//Check if any of the inventory weapons is craftable and add the craft actions to the Actions list.
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
			
		
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		return menu.showMenu(this, actions, display);
	}
	
	private void reloadAllGuns() {
		List<Item> items = this.getInventory();
		List<Gun> allGuns = new ArrayList<Gun>();
		List<Ammo> allAmmo = new ArrayList<Ammo>();
		for (Item item : items) {
			boolean itemIsGun = item.hasCapability(GunTargetCapability.DIRECTIONAL) || item.hasCapability(GunTargetCapability.SINGLE_TARGET);
			if (itemIsGun) {
				allGuns.add((Gun) item);
			}
			else if (item instanceof Ammo) {
				allAmmo.add((Ammo) item);
			}
		}
		
		if (allAmmo.size() > 0) {
			for (Ammo ammo : allAmmo) {
				for (Gun gun : allGuns) {
					if (ammo.getGun() == gun.toString()) {
						gun.reload(ammo.getReloadAmount());
						this.removeItemFromInventory(ammo);
						System.out.println("Reloaded " + gun + " with " + ammo.getReloadAmount() + " ammo");
					}
				}
			}
		}
	}
}
