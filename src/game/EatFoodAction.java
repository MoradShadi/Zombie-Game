package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class EatFoodAction extends Action {
	private int hpRestored;
	private Food foodToRemove;

	public EatFoodAction(Food initFood) {
		// TODO Auto-generated constructor stub
		foodToRemove = initFood;
		hpRestored = 10;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		actor.removeItemFromInventory(foodToRemove);
		actor.heal(hpRestored);
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " eats food to restore 10 health.";
	}

}
