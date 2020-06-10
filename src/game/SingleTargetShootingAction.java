package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class SingleTargetShootingAction extends Action {
	Sniper sniper;
	Actor targetZombie;

	public SingleTargetShootingAction(Sniper initSniper, Actor initTarget) {
		// TODO Auto-generated constructor stub
		this.sniper = initSniper;
		this.targetZombie = initTarget;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		AttackAction shootTarget = new AttackAction(this.targetZombie);
		int shotDmg = this.sniper.getShotDamage(this.targetZombie);
		double hitChance = this.sniper.getHitChance(this.targetZombie);
		return shootTarget.gunShotExecute(actor, map, shotDmg, hitChance);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " shoots " + this.targetZombie;
	}

}
