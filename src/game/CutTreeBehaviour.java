package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

public class CutTreeBehaviour implements Behaviour {

	public CutTreeBehaviour() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Checks whether the actor can cut the tree by checking if he is currently standing on a tree and has an axe in his inventory
	 * and the tree is old enough
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		Location actorLocation = map.locationOf(actor);
		if (actorLocation.getGround() instanceof Tree) {
			Tree tree = (Tree) actorLocation.getGround();
			if (tree.canCut()) {
				for (Item item : actor.getInventory()) {
					if (item.toString() == "axe") {
						return new CutTreeAction();
					}
				}
			}
		}
		
		return null;
	}

}
