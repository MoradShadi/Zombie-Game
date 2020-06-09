package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Special action for exiting the game
 *
 */
public class ExitAction extends Action {

	/**
	 * Constructor
	 */
	public ExitAction() {
	}

	/**
	 * Performs the exit game action
	 * 
	 * @param actor The actor exiting the game.
	 * @param map The map the actor is on.
	 * @return the menu description of the action.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		System.out.println("Thank you for playing our game. Goodbye!");
		System.exit(0);
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return "Exit game";
	}

}
