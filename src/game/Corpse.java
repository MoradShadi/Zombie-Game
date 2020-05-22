package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Location;

/**
 * Special portable item to represent human corpses that can turn into a zombie. The number of turns needed for it to
 * turn into a zombie is a randomly generated number between 5 to 10 turns.
 * 
 * @author User
 *
 */
public class Corpse extends PortableItem {
	private Random rand = new Random();
	
	private int turnCount;
	private int turnsNeeded;

	/**
	 * Constructor
	 * 
	 * @param name name of the Corpse
	 */
	public Corpse(String name) {
		super(name, 'C');
		// TODO Auto-generated constructor stub
		turnCount = 0;
		turnsNeeded = rand.nextInt(6) + 5; //Generates a random number from 0-5, then add 5, so it essentially generates a random number from 5-10 
	}
	
	/**
	 * Inform the carried Corpse that a turn has passed.
	 * This method is called once per turn if the Corpse is being carried.
	 * It will check if enough turns have passed for it to turn into a Zombie.
	 * If it has enough turns to turn into a Zombie, then remove the Corpse from the
	 * actors inventory and place a Zmobie at a random valid adjacent location
	 * beside the actor holding it.
	 * If it does not become a Zombie, then increase the turn count by 1 to keep track of
	 * how many turns have passed.
	 *
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
	 */
	@Override
	public void tick(Location currentLocation, Actor actor) {
		if (turnCount == turnsNeeded) {
			actor.removeItemFromInventory(this);
			Location corpseReviveLocation = getRandomAdjacentLocation(currentLocation, actor);
			corpseReviveLocation.addActor(new Zombie(name + " zombie"));
			System.out.println(name + " has turned into a zombie!");
		}
		
		turnCount++;
	}
	
	/**
     * Informs the Corpse that a turn has passed 
     * This method is called once per turn if the Corpse is on the ground.
	 * It will check if enough turns have passed for it to turn into a Zombie.
	 * If it has enough turns to turn into a Zombie, then remove the Corpse from the
	 * item location and place a Zmobie at that location.
	 * If it does not become a Zombie, then increase the turn count by 1 to keep track of
	 * how many turns have passed.
	 * 
     * @param currentLocation The location of the ground on which we lie.
     */
	@Override
	public void tick(Location currentLocation) {
		if (turnCount == turnsNeeded) {
			currentLocation.removeItem(this);
			currentLocation.addActor(new Zombie(name + " zombie"));
			System.out.println(name + " has turned into a zombie!");
		}
		
		turnCount++;
	}
	
	/**
	 * Gets a random adjacent location beside the actor
	 * 
	 * @param currentLocation location of the actor
	 * @param actor the actor who's adjacent location is needed
	 * @return
	 */
	private Location getRandomAdjacentLocation(Location currentLocation, Actor actor) {
		List<Exit> allAdjacentLocations = currentLocation.getExits();
		ArrayList<Location> validAdjacentLocations = new ArrayList<Location>();
		
		//Do while loop in case the actor holding the corpse is completely surrounded by other actors
		do {
			for (Exit exit : allAdjacentLocations) {
				Location adjacentLocation = exit.getDestination();
				boolean walkableGround = adjacentLocation.getGround().canActorEnter(actor);
				boolean notOccupied = !adjacentLocation.containsAnActor();
				if (walkableGround && notOccupied) {
					validAdjacentLocations.add(adjacentLocation);
				}
			}
			
			//Use a random adjacent location as the center point to search for valid adjacent locations again
			int randomIndex = rand.nextInt(allAdjacentLocations.size());
			allAdjacentLocations = allAdjacentLocations.get(randomIndex).getDestination().getExits();
		}
		while (validAdjacentLocations.isEmpty());
		
		int randomIndex = rand.nextInt(validAdjacentLocations.size());
		return validAdjacentLocations.get(randomIndex);
	}
	

}
