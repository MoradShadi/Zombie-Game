package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;

public class DriveCarAction extends Action {
	
	private Location destination;
	private String displayMsg;

	public DriveCarAction(Location initDestination, String initMessage) {
		// TODO Auto-generated constructor stub
		this.destination = initDestination;
		displayMsg = initMessage;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		return new MoveActorAction(destination, displayMsg).execute(actor, map);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " drives " + displayMsg;
	}

}
