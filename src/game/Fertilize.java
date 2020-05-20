package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class Fertilize {
	Location map;
	
	public Fertilize(Crop crop, GameMap gameMap) {
		crop.boostRipen();
	}
}

