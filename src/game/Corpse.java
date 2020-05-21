package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Location;

public class Corpse extends PortableItem {
	private Random rand = new Random();
	
	private int turnCount;
	private int turnsNeeded;

	public Corpse(String name) {
		super(name, 'C');
		// TODO Auto-generated constructor stub
		turnCount = 0;
		turnsNeeded = rand.nextInt(6) + 5; //Generates a random number from 0-5, then add 5, so it essentially generates a random number from 5-10 
	}
	
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
	
	@Override
	public void tick(Location currentLocation) {
		if (turnCount == turnsNeeded) {
			currentLocation.removeItem(this);
			currentLocation.addActor(new Zombie(name + " zombie"));
			System.out.println(name + " has turned into a zombie!");
		}
		
		turnCount++;
	}
	
	private Location getRandomAdjacentLocation(Location currentLocation, Actor actor) {
		List<Exit> allAdjacentLocations = currentLocation.getExits();
		ArrayList<Location> validAdjacentLocations = new ArrayList<Location>();
		
		//Do while loop in case the actor holding the corpse is completely surrounded by other actors
		do {
			for (Exit exit : allAdjacentLocations) {
				Location adjacentLocation = exit.getDestination();
				boolean walkableGround = adjacentLocation.getGround().canActorEnter(actor);
				boolean notOccupied = adjacentLocation.containsAnActor();
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
