package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class Fertilize implements Behaviour {
	Location location;
	Location[][] map;
	
	public void fertilizeCrop(Crop crop, GameMap gameMap) {
		crop.boostRipen();
	}

	@Override
	public Action getAction(Actor actor, GameMap gameMap) {
		// TODO Auto-generated method stub
		Farmer farmer = (Farmer) actor;
		location = gameMap.locationOf(farmer);
		int x = location.x();
		int y = location.y();
		
		Ground ground = gameMap.at(x, y).getGround();
		if(ground.getDisplayChar() == '^') {
			Crop crop = (Crop) ground;
			if(crop.isRipe() == false) {
				fertilizeCrop(crop, gameMap);
			}
		}
		return null;
	}
}

