package game;

import java.util.Random;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;

public class MyWorld extends World {
	MamboMarie voodooPriestess = new MamboMarie("Vodoo Priestess");
	
	Random rand = new Random();

	public MyWorld(Display display) {
		super(display);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean stillRunning() { 
		// stillRunning() is called at the start of every turn, so we use this to spawn mambo marie
		this.spawnMamboMarie();
		
		boolean playerAlive= actorLocations.contains(player);
		
		boolean zombiesRemaining = false;
		for (Actor actor : actorLocations) {
			if (actor.hasCapability(ZombieCapability.UNDEAD)) {
				zombiesRemaining = true;
				break;
			}
		}
		
		return playerAlive && zombiesRemaining;
	}
	
	private void spawnMamboMarie() {
		if (voodooPriestess.isConscious() && !voodooPriestess.getOnMap()) {
			GameMap map = this.gameMaps.get(rand.nextInt(2));
			
			if (rand.nextDouble() <= 0.05) {			
				int xMax = map.getXRange().max();
				int yMax = map.getYRange().max();
				int xCoordinate;
				int yCoordinate;
				boolean locationIsOccupied;
				
				do {
					double edgeToSpawn = rand.nextDouble();
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
					locationIsOccupied = !map.at(xCoordinate, yCoordinate).canActorEnter(voodooPriestess);
				}
				while (locationIsOccupied);

				map.at(xCoordinate, yCoordinate).addActor(voodooPriestess);
				this.voodooPriestess.setOnMap(true);
			}
		}
	}

}
