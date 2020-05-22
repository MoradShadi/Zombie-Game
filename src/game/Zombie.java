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
	
	private double loseLimbChance;
	
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
		loseLimbChance = 0.25;
		legs.add(new ZombieLeg());
		legs.add(new ZombieLeg());
		arms.add(new ZombieArm());
		arms.add(new ZombieArm());
	}
	
	/**
	 * Change the chance for the zombie to punch as opposed to other intrinsic attacks
	 * The new punch chance must be between 0 and 100 (inclusive)
	 * 
	 * @param newChance new punch chance of zombie
	 */
	private void setPunchChance(double newChance) {
		if (newChance >= 0 && newChance <= 100) {
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
	 * If the attack hits and if the current Zombie is carrying a weapons, then return
	 * that weapon. Otherwise, returns the Zombie's natural fighting 
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
	

	/**
	 * Creates and returns an intrinsic weapon.
	 *
	 * The Zombie has 2 possible intrinsic weapons, which is the ounch and bite.
	 * Each of them have a 50% chance of being use by default
	 *
	 * @return a freshly-instantiated IntrinsicWeapon
	 */
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
	 * Give the zombie a 10% chance of saying 'BRAAAAAAINS'
	 * The precedence for zombie actions can be seen in the order of arrangement for its behaviours.
	 * First priority for zombie is to pick up any weapon on the ground. If it already has a weapon, then it will throw
	 * its current weapon on a random adjacent location then swap for the new weapon on the ground.
	 * If there is no weapons on the ground, then Zombie will try to attack. 
	 * If it cannot attack, it will chase any human within 10 spaces.  
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
			
			if (action instanceof PickUpItemAction && !this.hasArm()) {
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
	
	/**
	 * Gets the number of arms remaining
	 * 
	 * @return number of arms remaining
	 */
	public int armCount() {
		return arms.size();
	}
	
	/**
	 * Gets the number of legs remaining
	 * 
	 * @return number of legs remaining
	 */
	public int legCount() {
		return legs.size();
	}
	
	/**
	 * Checks if the zombie has at least one arm
	 * 
	 * @return true if the zombie has at least one arm, false otherwise
	 */
	public boolean hasArm() {
		return arms.size() > 0;
	}
	
	/**
	 * Checks if the zombie has at least one leg
	 * 
	 * @return true if the zombie has at least one leg, false otherwise
	 */
	public boolean hasLeg() {
		return legs.size() > 0;
	}
	
	/**
	 * Checks if the zombie has a weapon in its inventory. The zombie is only allowed to hold one weapon at a time and nothing else
	 * 
	 * @return true if the zombie is holding a weapon
	 */
	private boolean hasWeapon() {
		List<Item> zombieInventory = getInventory(); //zombie can only pick up one item at a time
		if (zombieInventory.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Calls superclass hurt to deal damage to zombie
	 * Special hurt method for Zombie only so that it can return the dropped legs, arms and weapons when the zombie is damaged
	 * 
	 * @param points number of hitpoints to deduct
	 * @return the legs, arms and weapons that the zombie drops due to losing limbs
	 */
	public ArrayList<Item> zombieHurt(int points) {
		hurt(points);
		
		ArrayList<Item> droppedLimbsAndWeapons = loseLimbs();
		return droppedLimbsAndWeapons;
	}
	
	/**
	 * Gives the zombie a 25% chance to lose each limb type, if it has at least one of that particular limb type left.
	 * 
	 * @return the legs, arms and weapons that the zombie drops due to losing limbs
	 */
	private ArrayList<Item> loseLimbs() {
		double rollLoseArm = rand.nextDouble();
		double rollLoseLeg = rand.nextDouble();
		ArrayList<Item> droppedLimbsAndWeapons = null;
		
		boolean loseArm = hasArm() && rollLoseArm < this.loseLimbChance;
		boolean loseLeg = hasLeg() && rollLoseLeg < this.loseLimbChance;
			
		droppedLimbsAndWeapons = removeLimbs(loseArm, loseLeg);
		
		return droppedLimbsAndWeapons;
	}
	
	/**
	 * Gives the zombie a 25% chance to lose two of the limb type that it is losing, instead of one.
	 * 
	 * @param loseArm whether the zombie will be losing any arms
	 * @param loseLeg whether the zombie will be losing any legs
	 * @return the legs, arms and weapons that the zombie drops due to losing limbs
	 */
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
	
	/**
	 * Removes the specified number of arms and returns the lost arms. 
	 * If one arm is removed and there is one arm remaining, then the Zombie has a 50% chance of dropping
	 * the weapon it is holding, and the chance for it to punch is halved
	 * If it has zero arms remaining, then it will definitely drop the weapon it is holding and the chance
	 * for it to punch is set to 0%
	 * 
	 * @param numOfArmLost number of arms to be removed
	 * @return the ZombieArm objects that are removed and the weapon object that is dropped, if any
	 */
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
			inventory.remove(0);
			lostArmsAndWeapons.add(zombieWeapon);
			System.out.println(this + " dropped its " + zombieWeapon);
		}
		
		System.out.println(this + " lost " + numOfArmLost + " arm(s)");
		return lostArmsAndWeapons;
	}
	
	/**
	 * Removes the specified number of legs and returns the lost legs
	 * 
	 * @param numOfLegLost number of legs to be removed
	 * @return the ZombieLeg objects that are removed
	 */
	private ArrayList<ZombieLeg> removeLeg(int numOfLegLost) {
		ArrayList<ZombieLeg> lostLegs = new ArrayList<ZombieLeg>(numOfLegLost);
		
		for (int i = 0; i < numOfLegLost; i++) {
			lostLegs.add(legs.get(0));
			legs.remove(0);
		}
		
		System.out.println(this + " lost " + numOfLegLost + " leg(s)");
		return lostLegs;
	}
	
	/**
	 * Checks if the zombie is prohibited from doing any movements. If the zombie has no legs, or only has one leg and 
	 * already moved in the last turn, then it is not allowed to move
	 * 
	 * @param lastAction previous action, to check if it is a MoveActorAction
	 * @return true if the zombie is not allowed to move
	 */
	private boolean cannotMove(Action lastAction) {
		boolean stopMovement = false;
		boolean oneLegMovement = legCount() == 1 && lastAction instanceof MoveActorAction;
		
		//If zombie only has one leg and previous action is a MoveActorAction, it cannot move this turn, so it cannot hunt or wander
		if (oneLegMovement || !hasLeg()) {
			stopMovement = true;
		}
		
		return stopMovement;
	}
	
}
