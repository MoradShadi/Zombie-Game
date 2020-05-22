package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Special Action for fertilizing crops.
 */
public class FertilizeAction extends Action {
	Crop cropToBeFertilized;
	
	/**
	 * Constructor.
	 * 
	 * @param initCrop the crop to be fertilized
	 */
	public FertilizeAction(Crop initCrop) {
		// TODO Auto-generated constructor stub
		cropToBeFertilized = initCrop;
	}
	
	/**
	 * Override method to perform the action.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		cropToBeFertilized.fertilize();
		return menuDescription(actor);
	}

	/**
	 * Override method to print a description.
	 *
	 * @param actor The actor performing the action.
	 * @return a description of what happened that can be displayed to the user.
	 */
	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " fertilized a crop.";
	}

}
