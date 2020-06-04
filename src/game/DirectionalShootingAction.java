package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.NumberRange;

public class DirectionalShootingAction extends Action {
	Gun gun;
	Exit direction;
	
	public DirectionalShootingAction(Gun initGun, Exit initDirection) {
		// TODO Auto-generated constructor stub
		this.gun = initGun;
		this.direction = initDirection;
	}

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
		return this.menuDescription(shooter) + shotMessage;
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " shoots towards " + this.direction.getName();
	}
	


	private String cardinalDirectionShot(Actor shooter, Gun gun, int initialX, int initialY, int xChange, int yChange, GameMap map) {
		int range = this.gun.getRange();
		ArrayList<String> resultsLst = new ArrayList<String>();
		
		// When shooting north / south, it will iterate through each row (layer) in range, then loop through each location in that row
		// When shooting east / west, it will iterate through each column (layer) in range, then loop through each location in that column
		int layerCoordinate; // Layer coordinate is the x coordinate when shooting east / west, y coordinate when shooting north/south
		int innerCoordinate; // inner coordinate is the y coordinate when shooting east / west, x coordinate when shooting north/south
		int locationsInLayer = 1;
		int layerDirection;
		boolean horizontalShot;
		NumberRange layerRange;
		NumberRange innerRange;
		
		if (xChange != 0) { // shot direction is east or west
			layerCoordinate = initialX;
			innerCoordinate = initialY;
			layerDirection = xChange;
			horizontalShot = true;
			layerRange = map.getXRange();
			innerRange = map.getXRange();
		}
		else {
			layerCoordinate = initialY;
			innerCoordinate = initialX;
			layerDirection = yChange;
			horizontalShot = false;
			layerRange = map.getXRange();
			innerRange = map.getXRange();
		}
			
		for (int i = 0; i < range; i++) {
			layerCoordinate += layerDirection;
			if (!layerRange.contains(layerCoordinate)) {
				break;
			}
			innerCoordinate += 1;
			
			int currentInner = innerCoordinate;
			locationsInLayer += 2;
			
			for (int innerCounter = 0; innerCounter < locationsInLayer; innerCounter++) { 
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
