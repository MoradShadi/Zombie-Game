package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;

/**
 * A class that represents food.
 */
public class Food extends PortableItem {
	public Food() {
		super("Food", 'o');
	}
	
	@Override
	public List<Action> getAllowableActions() {
		allowableActions.add(new EatFoodAction(this));
		return allowableActions.getUnmodifiableActionList();
	}
}
