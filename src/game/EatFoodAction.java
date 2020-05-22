package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Special action for eating food
 * 
 * @author User
 *
 */
public class EatFoodAction extends Action {
	private Food foodToRemove;

	/**
	 * Constructor
	 * 
	 * @param initFood the food that will be eaten
	 */
	public EatFoodAction(Food initFood) {
		// TODO Auto-generated constructor stub
		foodToRemove = initFood;
	}

	/**
	 * Perform the eating action and heal the actor for the Food heal amount
	 * 
	 * @param actor The actor that is eating.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		actor.heal(foodToRemove.getHealAmount());
		actor.removeItemFromInventory(foodToRemove);
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " eats food to restore 10 health.";
	}

}
