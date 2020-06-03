package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class DirectionalShootingAction extends Action {
	int range;
	String direction;
	
	public DirectionalShootingAction(int initRange, String initDirection) {
		// TODO Auto-generated constructor stub
		this.range = initRange;
		this.direction = initDirection;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		return "LOLOL";
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " shoots toward " + direction;
	}

}
