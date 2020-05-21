package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

/**
 * Class representing the Farmer.
 */
public class Farmer extends Human {
	private Behaviour[] behaviours = {
			new HarvestBehaviour(),
			new FertilizeBehaviour(),
			new SowCropBehaviour(),
			new WanderBehaviour()
	};

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Farmer(String name) {
		super(name, 'F', 50);
	}
	
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		for (Behaviour behaviour : behaviours) {
			//First priority is harvesting food, second is fertilizing, third is planting crop, fourth is wander
			Action action = behaviour.getAction(this, map);
			if (action != null) {
				return action;
			}
		}
		return new DoNothingAction();
	}
}
