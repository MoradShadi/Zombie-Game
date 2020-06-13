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
			new ChantingBehaviour(this),
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
	public int getTurnCount() {
		return turnCount;
	}
	
	public void incrementTurnCount() {
		turnCount++;
	}
	
	public void resetTurnCount() {
		turnCount = 0;
	}
	
	public double getSpawnChance() {
		return this.spawnChance;
	}
	

	/**
	 * Handles the actions for mambo marie when she is on the map. She will try to chant and summon zombies if possible. If not, she will just 
	 * wander randomly like other zombies.
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (onMap){
			incrementTurnCount();
			
			for (Behaviour behaviour : behaviours) {
				Action action = behaviour.getAction(this, map);
				if (action != null) {
					return action;
				}
			}

			return new DoNothingAction();
		}
		return new DoNothingAction();
	}
}
