package game;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.MoveActorAction;

/**
 * A Zombie.
 * 
 * This Zombie is pretty boring.  It needs to be made more interesting.
 * 
 * @author ram
 *
 */
public class Zombie extends ZombieActor {
	Random rand = new Random();
	
	private Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.ALIVE),
			new HuntBehaviour(Human.class, 10),
			new WanderBehaviour()
	};
	
	private ArrayList<ZombieLeg> legs = new ArrayList<ZombieLeg>(2);
	
	private ArrayList<ZombieArm> arms = new ArrayList<ZombieArm>(2);
	
	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
		legs.add(new ZombieLeg());
		legs.add(new ZombieLeg());
		arms.add(new ZombieArm());
		arms.add(new ZombieArm());
	}
	

	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(10, "punches");
	}

	/**
	 * If a Zombie can attack, it will.  If not, it will chase any human within 10 spaces.  
	 * If no humans are close enough it will wander randomly.
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the current Zombie is
	 * @param display the Display where the Zombie's utterances will be displayed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		boolean oneLegAction = legCount() == 1 && lastAction instanceof MoveActorAction;
		if (oneLegAction || !hasLeg()) { 
			//If zombie only has one leg and previous action is a MoveActorAction, it cannot move this turn
			Action action = behaviours[0].getAction(this, map);
			if (action != null) {
				return action;
			}
			return new DoNothingAction();
		}

		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return new DoNothingAction();	
	}
	
	public int armCount() {
		return arms.size();
	}
	
	public int legCount() {
		return legs.size();
	}
	
	public boolean hasArm() {
		return arms.size() > 0;
	}
	
	public boolean hasLeg() {
		return legs.size() > 0;
	}
	
	public void removeArm(int numOfArmLost) {
		for (int i = 0; i < numOfArmLost; i++) {
			arms.remove(0);
		}
		if (armCount() == 1) {
			if (rand.nextBoolean()) {
				//continue implement drop weapon (need to allow zombie carry weapon first)
			}
		}
	}
	
	public void removeLeg(int numOfLegLost) {
		for (int i = 0; i < numOfLegLost; i++) {
			legs.remove(0);
		}
	}
	
}
