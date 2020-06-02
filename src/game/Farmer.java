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
	 * @param name Name of the farmer
	 */
	public Farmer(String name) {
		super(name, 'F', 50);
	}
	
	/**
	 * The precedence for zombie actions can be seen in the order of arrangement for its behaviours.
	 * First priority is to harvest any ripe crops if it can.
	 * If it cannot harvest anything, then it will try to fertilize the crop it is standing on.
	 * If there are no crops to fertilize, then it will try to sow a crop at an adjacent dirt ground.
	 * If it does not sow a crop, then it will wander around.
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
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
