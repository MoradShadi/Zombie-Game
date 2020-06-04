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
		NumberRange xRange = map.getXRange();
		NumberRange yRange = map.getYRange();
		
		if (xChange != 0) { // shot direction is east or west
			int xLayer = initialX;
			int topY = initialY;;
			int locationsInLayer = 1;
			
			for (int i = 0; i < range; i++) { //This will loop from one vertical column to the next column in the gun range
				xLayer += xChange;
				if (!xRange.contains(xLayer)) {
					break;
				}
				topY += 1;
				
				int currentY = topY;
				locationsInLayer += 2;
				
				for (int yCounter = 0; yCounter < locationsInLayer; yCounter++) { // This will loop from the top Y coordinate within the gun range to the bottom in each column
					if (yRange.contains(currentY)) {
						Location shotLocation = map.at(xLayer, currentY);
						if (shotLocation.containsAnActor()) {
							Actor target = map.getActorAt(shotLocation);
							AttackAction shootTarget = new AttackAction(target);
							
							resultsLst.add(shootTarget.gunShotExecute(shooter, map, this.gun.getShotDamage(), this.gun.getHitChance()));
						}
						currentY -= 1;
					}
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
