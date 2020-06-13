package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class that generates a chanting action for every 10 turns that mambo marie is on the map, and makes her vanish if she has been on the map
 * for 30 turns
 * 
 * @author User
 *
 */
public class ChantingBehaviour implements Behaviour {
	private MamboMarie mambo;
	
	public ChantingBehaviour(MamboMarie initMambo) {
		this.mambo = initMambo;
	}

	/**
	 * If mambo is on the map, thenvery 10 turns on the map, she will spawn 5 zombies and if she is not killed after 30 turns 
	 * then she vanishes from the map.
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		Action action = null;
		
		if (mambo.getOnMap()) {
			if (mambo.getTurnCount() % 10 == 0) {
				action = new ChantingAction();
				
				if (mambo.getTurnCount() % 30 == 0) {
					map.removeActor(mambo);
					mambo.resetTurnCount();;
					mambo.setOnMap(false);
					System.out.println("Voodoo priestess vanishes!");
				}
			}
		}

		return action;
	}

}
