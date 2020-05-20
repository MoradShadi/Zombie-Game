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
import edu.monash.fit2099.engine.PickUpItemAction;
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
			new PickUpWeaponBehaviour(),
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
	
	
	/**
	 * Get the weapon this Zombie is using and determine if the Zombie misses the attack.
	 * 
	 * The Zombie has a 50% chance to miss the attack, so return null if Zombie misses.
	 * If the attack is approved and if the current Zombie is carrying weapons, returns 
	 * the first one in the inventory. Otherwise, returns the Zombie's natural fighting 
	 * equipment e.g. punch or bite. However, if the Zombie decides to bite, it has a 
	 * further 50% chance to miss, so an overall chance of only 25% to hit the bite attack.
	 * If the bite attack hits successfully, heal the Zombie for 5 health.
	 *
	 * @return the Actor's weapon or natural
	 */
	@Override
	public Weapon getWeapon() {
		if (rand.nextBoolean()) {
			//50% base chance to miss
			return null;
		}
		
		Weapon zombieWeapon = super.getWeapon();
		
		if (zombieWeapon.verb() == "bites") {
			if (rand.nextBoolean()) {
				//bite has further 50% chance to miss, so overall it has 50% * 50% = 25% chance to hit
				//other weapons or punch have normal base 50% chance to hit
				return null;
			}
			else {
				//if the bite doesn't miss, it heals the zombie for 5 hp
				heal(5);
			}
		}
		
		return zombieWeapon;
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
		
		double brainsDialogueChance = rand.nextDouble();
		if (brainsDialogueChance <= 0.1)
			System.out.println(this.name+ " says BRAAAAAAINS");

		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			
			if (lastAction instanceof PickUpItemAction && action instanceof PickUpItemAction) {
				action = null;
			}
			
			if (action instanceof MoveActorAction && cannotMove(lastAction)) {
				action = null;
			}
			
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
	
	private boolean cannotMove(Action lastAction) {
		boolean stopMovement = false;
		boolean oneLegMovement = legCount() == 1 && lastAction instanceof MoveActorAction;
		
		//If zombie only has one leg and previous action is a MoveActorAction, it cannot move this turn, so it cannot hunt or wander,
		//only can attack unless there is weapon on ground, then picking up weapon has precedence 
		if (oneLegMovement || !hasLeg()) {
			stopMovement = true;
		}
		
		return stopMovement;
	}
	
}
