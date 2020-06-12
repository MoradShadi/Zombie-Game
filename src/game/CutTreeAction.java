package game;


import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class CutTreeAction extends Action {
	Random rand = new Random();

	public CutTreeAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		Location actorLocation = map.locationOf(actor);
		actorLocation.setGround(new Dirt());
		List<Exit> exits = actorLocation.getExits();
		
		for (int i = 0; i < 3; i++) {
			int randomIndex = rand.nextInt(exits.size());
			Location randomLocation = exits.get(randomIndex).getDestination();
			randomLocation.addItem(new Plank());
		}
		
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " chops tree";
	}

}
