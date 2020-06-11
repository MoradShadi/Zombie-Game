package game;

import java.util.Random;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;

public class MyWorld extends World {
	MamboMarie voodooPriestess;
	boolean gameWin;
	
	Random rand = new Random();

	public MyWorld(Display display) {
		super(display);
		// TODO Auto-generated constructor stub
		this.voodooPriestess = new MamboMarie();
		this.gameWin = false;
	}
	
	/**
	 * Run the game.
	 *
	 * On each iteration the gameloop does the following: - displays the player's
	 * map - processes the actions of every Actor in the game, regardless of map
	 *
	 * We could either only process the actors on the current map, which would make
	 * time stop on the other maps, or we could process all the actors. We chose to
	 * process all the actors.
	 *
	 * @throws IllegalStateException if the player doesn't exist
	 */
	@Override
	public void run() {
		if (player == null)
			throw new IllegalStateException();

		// initialize the last action map to nothing actions;
		for (Actor actor : super.actorLocations) {
			lastActionMap.put(actor, new DoNothingAction());
		}

		// This loop is basically the whole game
		while (this.stillRunning()) {
			GameMap playersMap = super.actorLocations.locationOf(player).map();
			playersMap.draw(display);

			// Process all the actors.
			for (Actor actor : super.actorLocations) {
				if (this.stillRunning())
					processActorTurn(actor);
			}

			// Tick over all the maps. For the map stuff.
			for (GameMap gameMap : super.gameMaps) {
				gameMap.tick();
			}

			this.spawnMamboMarie();
		}
		display.println(endGameMessage());
	}
	
	@Override
	protected boolean stillRunning() { 		
		boolean playerAlive= super.actorLocations.contains(player);
		
		boolean zombiesRemaining = false;
		for (Actor actor : actorLocations) {
			if (actor.hasCapability(ZombieCapability.UNDEAD)) {
				zombiesRemaining = true;
				break;
			}
		}
		if (!zombiesRemaining) {
			if (voodooPriestess.isConscious()) {
				zombiesRemaining = true;
			}
			else {
				this.gameWin = true;
			}
		}
		
		return playerAlive && zombiesRemaining;
	}
	
	/**
	 * Return a string that can be displayed when the game ends depending on whether the player loses or wins.
	 *
	 * @return the string "Victory!" if the player wins, "Game Over" if the player dies
	 */
	@Override
	protected String endGameMessage() {
		if (gameWin) {
			return "Victory!";
		}
		else {
			return "PLayer loses. Game Over";
		}
	}
	
	private void spawnMamboMarie() {
		if (voodooPriestess.isConscious() && !voodooPriestess.getOnMap()) {
			GameMap map = super.gameMaps.get(rand.nextInt(2));
			
			if (rand.nextDouble() <= this.voodooPriestess.getSpawnChance()) {			
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
