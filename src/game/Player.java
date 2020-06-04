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
	MamboMarie VodooPriestess = new MamboMarie("Vodoo Priestess");
	
	private Random rand = new Random();

	private Menu menu = new Menu();
	
	private Behaviour[] behaviours = {
			new EatFoodBehaviour(),
			new HarvestBehaviour()
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
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (!map.contains(VodooPriestess)) {
			if (rand.nextFloat() < 0.05) {
				List<ArrayList<Integer>> listOfLists = new ArrayList<ArrayList<Integer>>();
				ArrayList<Integer> leftedgecoordinate =new ArrayList<Integer>();
				leftedgecoordinate.add(map.getXRange().min());
				leftedgecoordinate.add(rand.nextInt(map.getYRange().max()));
				
				ArrayList<Integer> rightedgecoordinate =new ArrayList<Integer>();
				rightedgecoordinate.add(map.getXRange().max());
				rightedgecoordinate.add(rand.nextInt(map.getYRange().max()));
				
				ArrayList<Integer> topedgecoordinate =new ArrayList<Integer>();
				topedgecoordinate.add(rand.nextInt(map.getXRange().max()));
				topedgecoordinate.add(map.getYRange().max());
				
				ArrayList<Integer> bottomedgecoordinate =new ArrayList<Integer>();
				bottomedgecoordinate.add(rand.nextInt(map.getXRange().max()));
				bottomedgecoordinate.add(map.getYRange().min());
				
				listOfLists.add(leftedgecoordinate);
				listOfLists.add(rightedgecoordinate);
				listOfLists.add(topedgecoordinate);
				listOfLists.add(bottomedgecoordinate);
				
				int coordinates = rand.nextInt(listOfLists.size());
				while (!map.at(listOfLists.get(coordinates).get(0),listOfLists.get(coordinates).get(1)).canActorEnter(this)){
					coordinates = rand.nextInt(listOfLists.size());
				}
				map.at(listOfLists.get(coordinates).get(0),listOfLists.get(coordinates).get(1)).addActor(VodooPriestess);
			}
		}
		
		if (VodooPriestess.getMamboMarieturnCount()%30 ==0) {
			map.removeActor(VodooPriestess);
			VodooPriestess.IterateMamboMarieturnCount();
		}
		
		if (!VodooPriestess.isConscious()) {
			map.removeActor(VodooPriestess);
			VodooPriestess = null;
		}
		
		//Check if any of the inventory weapons is craftable and add the craft actions to the Actions list.
		List<Item> items = this.getInventory();	
		for (Item item: items) {
			if (item.hasCapability(CraftableWeaponCapability.CRAFTABLE)) {
				actions.add(new CraftWeaponAction(item));
			}
			if (item.hasCapability(GunTargetCapability.DIRECTIONAL)) {
				actions.add(new ShootingSubmenu((Gun) item, display));
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
	
}
