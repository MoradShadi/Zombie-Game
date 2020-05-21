package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
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
	
	private double punchChance;
	
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
		setPunchChance(0.5);
		legs.add(new ZombieLeg());
		legs.add(new ZombieLeg());
		arms.add(new ZombieArm());
		arms.add(new ZombieArm());
	}
	
	private void setPunchChance(double newChance) {
		if (newChance >= 0) {
			this.punchChance = newChance;
		}
	}
	
	private double getPunchChance() {
		return this.punchChance;
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
	 * @return the Actor's weapon or natural attack
	 */
	@Override
	public Weapon getWeapon() {
		//We override the getWeapon method for all characters so that there is more adjustability for the miss rate for each character
		//and we can encapsulate the miss rate for each character in their own class
		if (rand.nextBoolean()) {
			//50% base chance to miss
			return null;
		}
		
		Weapon zombieWeapon = super.getWeapon();
		
		if (zombieWeapon.verb() == "bites") {
			if (rand.nextBoolean()) {
				//bite has further 50% chance to miss, so overall it has 50% * 50% = 25% chance to hit
				//other weapons and normal punch have normal base 50% chance to hit
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
		double rollIntrinsicWeapon = rand.nextDouble();
		if (rollIntrinsicWeapon < getPunchChance()) {
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
	
	private boolean hasWeapon() {
		List<Item> zombieInventory = getInventory(); //zombie can only pick up one item at a time
		if (zombieInventory.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public ArrayList<Item> zombieHurt(int points) {
		hurt(points);
		
		ArrayList<Item> droppedLimbsAndWeapons = loseLimbs();
		return droppedLimbsAndWeapons;
	}
	
	private ArrayList<Item> loseLimbs() {
		double rollLoseLimb = rand.nextDouble();
		ArrayList<Item> droppedLimbsAndWeapons = null;
		
		if (rollLoseLimb <= 0.95) {
			boolean loseArm = false;
			boolean loseLeg = false;
			
			if (hasArm() && hasLeg()) { //If zombie has at least one arm and one leg, then it has chance to lose both an arm and a leg
				boolean rollArmOrLeg = rand.nextBoolean();
				boolean loseArmAndLeg;
				
				if (rollArmOrLeg) {
					loseArm = true;
					loseArmAndLeg = rand.nextDouble() < 0.25;
					if (loseArmAndLeg) { //25% chance to lose leg also 
						loseLeg = true;
					}
				}
				else {
					loseLeg = true;
					loseArmAndLeg = rand.nextDouble() < 0.25;
					if (loseArmAndLeg) { //25% chance to lose arm also
						loseArm = true;
					}
				}
			}
			else if (hasArm()) { //If zombie has arm only then it can't lose any legs, can only lose arm
				loseArm = true;
			}
			else if (hasLeg()) { //If zombie has leg only then it can't lose any arm, can only lose leg
				loseLeg = true;
			}
			
			droppedLimbsAndWeapons = removeLimbs(loseArm, loseLeg);
		}
		return droppedLimbsAndWeapons;
	}
	
	private ArrayList<Item> removeLimbs(boolean loseArm, boolean loseLeg) {
		ArrayList<Item> droppedLimbsAndWeapons = new ArrayList<Item>();
		ArrayList<Item> droppedArmsAndWeapons = new ArrayList<Item>();
		ArrayList<ZombieLeg> droppedLegs = new ArrayList<ZombieLeg>();
		
		if (loseArm) {
			boolean loseTwoArms = (armCount() == 2) && (rand.nextDouble() < 0.25); //25% chance to lose two arms instead of one
			if (loseTwoArms) {
				droppedArmsAndWeapons = removeArm(2);
			}
			else {
				droppedArmsAndWeapons = removeArm(1);
			}
		}
		
		if (loseLeg) {
			boolean loseTwoLegs = (legCount() == 2) && (rand.nextDouble() < 0.25); //25% chance to lose two legs instead of one
			if (loseTwoLegs) {
				droppedLegs = removeLeg(2);
			}
			else {
				droppedLegs = removeLeg(1);
			}
		}
		
		for (Item armOrWeapon : droppedArmsAndWeapons) {
			droppedLimbsAndWeapons.add(armOrWeapon);
		}
		for (ZombieLeg leg : droppedLegs) {
			droppedLimbsAndWeapons.add(leg);
		}
		
		return droppedLimbsAndWeapons;
	}
	
	private ArrayList<Item> removeArm(int numOfArmLost) {
		ArrayList<Item> lostArmsAndWeapons = new ArrayList<Item>(numOfArmLost);
		
		for (int i = 0; i < numOfArmLost; i++) {
			lostArmsAndWeapons.add(arms.get(0));
			arms.remove(0);
		}
		
		//After rmeoving arm, reduce punch chance and check if zombie will drop the weapon.
		boolean dropWeapon = false;
		if (armCount() == 1) {
			setPunchChance(0.25); //punch chance is halved if lose one arm
			if (hasWeapon()) {
				dropWeapon = rand.nextBoolean(); //if zombie also has weapon, then 50% chance to drop weapon
			}	
		}
		else if (!hasArm()) {
			setPunchChance(0); //cannot punch if no arms remaining
			if (hasWeapon()) {
				dropWeapon = true; //if zombie has weapon, drop the weapons
			}
		}
		if (dropWeapon) {
			Item zombieWeapon = inventory.get(0);
			lostArmsAndWeapons.add(zombieWeapon);
			System.out.println(this + " dropped its " + zombieWeapon);
		}
		
		System.out.println(this + " lost " + numOfArmLost + " arm(s)");
		return lostArmsAndWeapons;
	}
	
	private ArrayList<ZombieLeg> removeLeg(int numOfLegLost) {
		ArrayList<ZombieLeg> lostLegs = new ArrayList<ZombieLeg>(numOfLegLost);
		
		for (int i = 0; i < numOfLegLost; i++) {
			lostLegs.add(legs.get(0));
			legs.remove(0);
		}
		
		System.out.println(this + " lost " + numOfLegLost + " leg(s)");
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
