package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import java.util.Random;

/**
 * An action that allows the actor to chant and summon five zombies at random locations.
 * 
 * @author User
 *
 */
public class ChantingAction extends Action {
	private static int counter = 0;
	
	public ChantingAction() {
	}

	/**
	 * Generates 5 random locations and creates a new zombie in that location if it is valid. The zombies are named using a static counter
	 * to differentiate between different zombies.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		int randXLocation;
		int randYLocation;
		
		for (int i = 0; i < 5; i++) {
			do {
				randXLocation = new Random().nextInt(map.getXRange().max() + 1);
				randYLocation = new Random().nextInt(map.getYRange().max() + 1);
			}
			while (!map.at(randXLocation, randYLocation).canActorEnter(actor));
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
