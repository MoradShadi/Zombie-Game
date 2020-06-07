package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class AimingAction extends Action {
	Zombie target;
	Sniper sniper;

	public AimingAction(Sniper initSniper, Zombie initTarget) {
		// TODO Auto-generated constructor stub
		this.target = initTarget;
		this.sniper = initSniper;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		this.sniper.aimSniper(target);
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " aims at " + this.target;
	}

}
