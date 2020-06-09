package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class that generates an ExitAction if the Actor choose to exit the game.
 *
 */
public class ExitBehaviour implements Behaviour {
	
	/**
	 * Constructor.
	 */
	public ExitBehaviour() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns an DriveCarAction that allows Actor to drive to another location.
	 * 
	 * @param actor The actor driving the car
	 * @param map The map the actor is on
	 * @return the ExitAction that allows Actor to to exit the game
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		return new ExitAction();
	}

}
