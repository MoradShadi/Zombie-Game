package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * An action that allows the Actor to aim a gun for increased accuracy and damage
 * 
 * @author User
 *
 */
public class AimingAction extends Action {
	Actor target;
	Sniper sniper;

	/**
	 * Constructor
	 * 
	 * @param initSniper sniper that is used for aiming
	 * @param initTarget the target that is aimed at
	 */
	public AimingAction(Sniper initSniper, Actor initTarget) {
		// TODO Auto-generated constructor stub
		this.target = initTarget;
		this.sniper = initSniper;
	}

	/**
	 * Perform the aiming action and adjust the accuracy and damage accordingly by calling the aimSniper method
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		this.sniper.aimSniper(target);
		return menuDescription(actor) + " for " + this.sniper.getAimCount() + " turn(s)";
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " aims at " + this.target;
	}

}
