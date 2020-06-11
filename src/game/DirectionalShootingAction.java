package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.NumberRange;

/**
 * An action that allows directional shooting in a 90 degree area with variable gun range.
 * 
 * @author User
 *
 */
public class DirectionalShootingAction extends Action {
	Gun gun;
	Exit direction;
	
	/**
	 * Constructor
	 * 
	 * @param initGun gun that will be used to shoot
	 * @param initDirection direction of the shot
	 */
	public DirectionalShootingAction(Gun initGun, Exit initDirection) {
		// TODO Auto-generated constructor stub
		this.gun = initGun;
		this.direction = initDirection;
	}

	/**
	 * Perform the shot by calculating the area of effect of the shot and damaging all actors in that aoe accordingly.
	 * 
	 */
	@Override
	public String execute(Actor shooter, GameMap map) {
		// TODO Auto-generated method stub
		String shotMessage = "";
		Location actorLocation = map.locationOf(shooter);
		String directionName = direction.getName();
		
		int initialX = actorLocation.x();
		int initialY = actorLocation.y();
		int xDirection = this.direction.getDestination().x();
		int yDirection = this.direction.getDestination().y();
		
		int xChange = xDirection - initialX;
		int yChange = yDirection - initialY;
		
		if (directionName.length() > 0 && directionName.length() <= 5) { // if direction name length <= 5, means it can be north, south, east or west only
			shotMessage = this.cardinalDirectionShot(shooter, this.gun, initialX, initialY, xChange, yChange, map);
		}
		else { // if direction name length > 5, means it is NE, SE, NW, SW only
			shotMessage = this.intercardinalDirectionShot(shooter, this.gun, initialX, initialY, xChange, yChange, map);
		}
		return this.menuDescription(shooter) + shotMessage;
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " shoots towards " + this.direction.getName();
	}
	
	/**
	 * Algorithm to loop through each location in the cardinal direction aoe of the gun and executes an AttackAction if the location contains an actor
	 * 
	 * @param shooter the actor that shoots the gun
	 * @param gun the gun that is used to shoot
	 * @param initialX x coordinate of shooter
	 * @param initialY y coordinate of shooter
	 * @param xChange change in x coordinate in the shot direction
	 * @param yChange change in y coordinate in the shot direction
	 * @param map map that the shooter is on
	 * @return display message showing the shot results for all actors in the aoe, whether the shot hits or misses
	 */
	private String cardinalDirectionShot(Actor shooter, Gun gun, int initialX, int initialY, int xChange, int yChange, GameMap map) { // N, S, E, W
		int range = this.gun.getRange();
		ArrayList<String> resultsLst = new ArrayList<String>();
		
		// When shooting north / south, it will iterate through each row (layer) in range, then loop through each location in that row
		// When shooting east / west, it will iterate through each column (layer) in range, then loop through each location in that column
		int layerCoordinate; // Layer coordinate is the x coordinate when shooting east / west, y coordinate when shooting north/south
		int firstInnerCoordinate; // inner coordinate is the y coordinate when shooting east / west, x coordinate when shooting north/south
		int locationsInLayer = 1;
		int layerDirection;
		boolean horizontalShot;
		NumberRange layerRange;
		NumberRange innerRange;
		
		if (xChange != 0) { // shot direction is east or west
			layerCoordinate = initialX;
			firstInnerCoordinate = initialY;
			layerDirection = xChange;
			horizontalShot = true;
			layerRange = map.getXRange();
			innerRange = map.getYRange();
		}
		else { // shot direction is north or south
			layerCoordinate = initialY;
			firstInnerCoordinate = initialX;
			layerDirection = yChange;
			horizontalShot = false;
			layerRange = map.getYRange();
			innerRange = map.getXRange();
		}
			
		for (int i = 0; i < range; i++) { // looping through the layer coordinate (if the range is 3, then there will be 3 layers)
			layerCoordinate += layerDirection; // the layer coordinate is incremented/decreased according to the shot direction
			if (!layerRange.contains(layerCoordinate)) { // if the layer exceeds the map range, then break the loop
				break;
			}
			firstInnerCoordinate += 1;
			
			int currentInner = firstInnerCoordinate;
			locationsInLayer += 2; // when the layer goes further, the number of locations increases by 2 each time
			
			for (int innerCounter = 0; innerCounter < locationsInLayer; innerCounter++) { // looping through inner coordinate
				// if the inner coordinate exceeds the map range, just continue to the next coordinate because there might be more valid locations in the next layer
				if (innerRange.contains(currentInner)) { 
					Location shotLocation;
					if (horizontalShot) {
						shotLocation = map.at(layerCoordinate, currentInner);
					}
					else {
						shotLocation = map.at(currentInner, layerCoordinate);
					}
					
					if (shotLocation.containsAnActor()) {
						Actor target = map.getActorAt(shotLocation);
						AttackAction shootTarget = new AttackAction(target);
						
						resultsLst.add(shootTarget.gunShotExecute(shooter, map, this.gun.getShotDamage(), this.gun.getHitChance()));
					}
					currentInner -= 1;
				}
			}
		}
		
		return this.generateShotResult(resultsLst);
		
	}
	
	/**
	 * Algorithm to loop through each location in the intercardinal direction aoe of the gun and executes an AttackAction if the location contains an actor
	 * 
	 * @param shooter the actor that shoots the gun
	 * @param gun the gun that is used to shoot
	 * @param initialX x coordinate of shooter
	 * @param initialY y coordinate of shooter
	 * @param xChange change in x coordinate in the shot direction
	 * @param yChange change in y coordinate in the shot direction
	 * @param map map that the shooter is on
	 * @return display message showing the shot results for all actors in the aoe, whether the shot hits or misses
	 */
	private String intercardinalDirectionShot(Actor shooter, Gun gun, int initialX, int initialY, int xChange, int yChange, GameMap map) { // NE, NW, SE, SW
		int range = this.gun.getRange();
		ArrayList<String> resultsLst = new ArrayList<String>();
		
		int yLayer = initialY;
		NumberRange yRange = map.getYRange();
		NumberRange xRange = map.getXRange();
		
		for (int yCounter = 0; yCounter < range + 1; yCounter++) { //outer loop will loop through each y coordinate
			int innerX;
			int xCounterLimit;
			
			if (yCounter == 0) {
				innerX = initialX + xChange;
				xCounterLimit = range;
			}
			else {
				innerX = initialX;
				xCounterLimit = range + 1;
			}
			
			for (int innerCounter = 0; innerCounter < xCounterLimit; innerCounter++) { // inner loop will loop through each x coordinate
				if (!xRange.contains(innerX)) {
					break;
				}
				
				Location shotLocation = map.at(innerX, yLayer);
				if (shotLocation.containsAnActor()) {
					Actor target = map.getActorAt(shotLocation);
					AttackAction shootTarget = new AttackAction(target);
					
					resultsLst.add(shootTarget.gunShotExecute(shooter, map, this.gun.getShotDamage(), this.gun.getHitChance()));
				}
				innerX += xChange;
			}
			
			yLayer += yChange;
			if (!yRange.contains(yLayer)) {
				break;
			}
		}
		
		return this.generateShotResult(resultsLst);
	}
	
	/**
	 * Generates the console output string based on a list of all the shot results, whether the shot misses or hits the actor
	 * 
	 * @param resultsLst an ArrayList of all the shot results on a Actor
	 * @return complete console message with proper line separator
	 */
	private String generateShotResult(ArrayList<String> resultsLst) {
		String appendedResult = "";
		for (int i = 0; i < resultsLst.size(); i++) {
			if (i == 0) {
				appendedResult += System.lineSeparator();
			}
			
			if (i == resultsLst.size() - 1) {
				appendedResult += resultsLst.get(i);
			}
			else {
				appendedResult += resultsLst.get(i) + System.lineSeparator();
			}
		}
		return appendedResult;
	}
}
