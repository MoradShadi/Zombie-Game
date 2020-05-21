package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class EatFoodBehaviour implements Behaviour {

	public EatFoodBehaviour() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action getAction(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		ZombieActor downcastActor = (ZombieActor) actor;
		if (downcastActor.isDamaged()) {
			Food food = this.getFood(downcastActor.getInventory());
			if (food != null) {
				return new EatFoodAction(food);
			}
		}
		return null;
	}
	
	private Food getFood(List<Item> inventory) {
		for (Item item : inventory) {
			if (item instanceof Food) {
				return (Food) item;
			}
		}
		return null;
	}

}
