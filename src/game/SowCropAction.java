package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Special Action for harvesting crops.
 */
public class SowCropAction extends Action {
	private Location cropLocation;

	/**
	 * Constructor.
	 * 
	 * @param initCropLocation the location of the crop
	 */
	public SowCropAction(Location initCropLocation) {
		// TODO Auto-generated constructor stub
		this.cropLocation = initCropLocation;
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
		cropLocation.setGround(new Crop());
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
		return actor + " planted a crop.";
	}

}
