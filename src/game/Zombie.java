package game;

import java.util.ArrayList;
import java.util.Random;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.MoveActorAction;
import edu.monash.fit2099.engine.Weapon;

/**
 * A Zombie.
 * 
 * This Zombie is pretty boring.  It needs to be made more interesting.
 * 
 * @author ram
 *
 */
public class Zombie extends ZombieActor {
	private Random rand = new Random();
	
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
	public Weapon getWeapon() {
		if (rand.nextBoolean()) {
			//50% base chance to miss
			return null;
		}
		
		for (Item item : inventory) {
			if (item.asWeapon() != null)
				return item.asWeapon();
		}
		
		IntrinsicWeapon intrinsicWeapon = getIntrinsicWeapon();
		if (intrinsicWeapon.verb() == "bites") {
			if (rand.nextBoolean()) {
				//Further 50% chance to miss bite, so chance of bite to hit is 50% * 50% = 25%
				heal(5);
				return intrinsicWeapon;
			}
		}
		return intrinsicWeapon;
	}
	

	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		if (rand.nextBoolean()) {
			return new IntrinsicWeapon(10, "punches");
		}
		else {
			return new IntrinsicWeapon(20,"bites");
		}
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
		double x = Math.random();
		if (x <= 0.1)
			System.out.println(this.name+ " says BRAAAAAAINS");
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
	
	public ArrayList<ZombieLimb> removeLimbs(int numOfArmLost, int numOfLegLost) {
		ArrayList<ZombieLimb> lostLimbs = new ArrayList<ZombieLimb>(numOfArmLost + numOfLegLost);
		
		if (numOfArmLost > 0) {
			ArrayList<ZombieArm> lostArms = removeArm(numOfArmLost);
			for (ZombieArm lostArm : lostArms) {
				lostLimbs.add(lostArm);
			}
		}
		
		if (numOfLegLost > 0) {
			ArrayList<ZombieLeg> lostLegs = removeLeg(numOfLegLost);
			for (ZombieLeg lostLeg : lostLegs) {
				lostLimbs.add(lostLeg);
			}
		}
		
		return lostLimbs;
	}
	
	private ArrayList<ZombieArm> removeArm(int numOfArmLost) {
		ArrayList<ZombieArm> lostArms = new ArrayList<ZombieArm>(numOfArmLost);
		
		for (int i = 0; i < numOfArmLost; i++) {
			lostArms.add(arms.get(0));
			arms.remove(0);
		}
		
		if (armCount() == 1) {
			if (rand.nextBoolean()) {
				//continue implement drop weapon (need to allow zombie carry weapon first)
			}
		}
		
		return lostArms;
	}
	
	private ArrayList<ZombieLeg> removeLeg(int numOfLegLost) {
		ArrayList<ZombieLeg> lostLegs = new ArrayList<ZombieLeg>(numOfLegLost);
		
		for (int i = 0; i < numOfLegLost; i++) {
			lostLegs.add(legs.get(0));
			legs.remove(0);
		}
		
		return lostLegs;
	}
	
}
