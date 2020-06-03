package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Weapon;

public class Car extends Item {
	private Location destination;
	private String destinationMsg;

	public Car(Location initDestination, String initMessage) {
		super("car", 'V', false);
		// TODO Auto-generated constructor stub
		destination = initDestination;
		destinationMsg = initMessage;
	}

	public List<Action> getAllowableActions() {
		Actions actionsList = new Actions();
		actionsList.add(new DriveCarAction(destination, destinationMsg));
		return actionsList.getUnmodifiableActionList();
	}
	
}
