package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;

public class FertilizeBehaviour implements Behaviour {

	public FertilizeBehaviour() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action getAction(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		Ground farmerGround = map.locationOf(actor).getGround();
		if (farmerGround.getDisplayChar() == '^') {
			Crop crop = (Crop) farmerGround;
			if (!crop.isRipe()) {
				return new FertilizeAction(crop);
			}
		}
		
		return null;
	}

}
