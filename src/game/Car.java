package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents a car for the player to use so that he can travel between different maps.
 */
public class Car extends Item {
	private Location destination;
	private String destinationMsg;

	/**
	 * Constructor
	 * 
	 * @param initDestination The destination to drive the car to
	 * @param initMessage The message which indicates to destination
	 */
	public Car(Location initDestination, String initMessage) {
		super("car", 'V', false);
		// TODO Auto-generated constructor stub
		destination = initDestination;
		destinationMsg = initMessage;
	}

	/**
	 * Returns a 
	 * 
	 * @return a list of unmodifiable actions
	 */
	public List<Action> getAllowableActions() {
		Actions actionsList = new Actions();
		actionsList.add(new DriveCarAction(destination, destinationMsg));
		return actionsList.getUnmodifiableActionList();
	}
	
}
