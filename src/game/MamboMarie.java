package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

/**
 * A voodoo priestess that summons zombies and can vanish
 * 
 * @author User
 *
 */
public class MamboMarie extends ZombieActor {
	
	private int turnCount;
	private boolean onMap = false;
	private double spawnChance;
	
	private Behaviour[] behaviours = {
			new ChantingBehaviour(),
			new WanderBehaviour()
	};
	

	public MamboMarie() {
		super("Vodoo Priestess",'M',100, ZombieCapability.UNDEAD);
		turnCount = 0;
		spawnChance = 0.05;
		// TODO Auto-generated constructor stub
	}
	
	public boolean getOnMap() {
		return onMap;
	}
	
	public void setOnMap(boolean input) {
		onMap = input;
	}
	public int getMamboMarieturnCount() {
		return turnCount;
	}
	
	public void incrementMamboMarieturnCount() {
		turnCount++;
	}
	
	public double getSpawnChance() {
		return this.spawnChance;
	}
	

	/**
	 * Handles the turn for mambo marie if she is currently on the map and also keeps track of how many turns she has been on the map.
	 * Every 10 turns on the map, she will spawn 5 zombies and if she is not killed after 30 turns then she vanishes from the map.
	 * 
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (onMap){
			incrementMamboMarieturnCount();

			if (getMamboMarieturnCount() % 10 == 0) {
				Action action = behaviours[0].getAction(this, map);
				
				if (getMamboMarieturnCount() % 30 == 0) {
					map.removeActor(this);
					this.turnCount = 0;
					setOnMap(false);
					System.out.println("Voodoo priestess has vanished");
				}
				
				if (action != null) {
					return action;
				}
			}
	
			Action action = behaviours[1].getAction(this, map);
			if (action != null) {
				return action;
			}

		}
		return new DoNothingAction();
	}
}
