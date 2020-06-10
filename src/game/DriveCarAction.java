package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;

/**
 * Special action for driving a car
 *
 */
public class DriveCarAction extends Action {
	
	private Location destination;
	private String displayMsg;

	/**
	 * Constructor
	 * 
	 * @param initDestination the destination to drive to
	 * @param initMessage the message which indicates the destination
	 */
	public DriveCarAction(Location initDestination, String initMessage) {
		// TODO Auto-generated constructor stub
		this.destination = initDestination;
		displayMsg = initMessage;
	}

	/**
	 * Performs the driving action
	 * 
	 * @param actor The actor that is driving.
	 * @param map The map the actor is on.
	 * @return the execution of moving the actor to the destination
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		new MoveActorAction(destination, displayMsg).execute(actor, map);
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " drives " + displayMsg;
	}

}
