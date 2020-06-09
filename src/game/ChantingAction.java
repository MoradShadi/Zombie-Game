package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import java.util.Random;

public class ChantingAction extends Action {
	private static int counter = 0;

	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 5; i++) {
			int randXLocation = new Random().nextInt(map.getXRange().max());
			int randYLocation = new Random().nextInt(map.getYRange().max());
			while (!map.at(randXLocation, randYLocation).canActorEnter(actor)){
				randXLocation = new Random().nextInt(map.getXRange().max());
				randYLocation = new Random().nextInt(map.getYRange().max());
			}
			counter += 1;
			String name = "Vodoo Zombie " + counter;
			map.at(randXLocation, randYLocation).addActor(new Zombie(name));
		}
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " is chanting! There are now 5 new zombies!";
	}

}
