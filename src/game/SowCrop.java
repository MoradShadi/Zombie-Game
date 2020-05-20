package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class SowCrop {
	Location map;
	
	public SowCrop(int x, int y, GameMap gameMap) {
		Crop crop = new Crop(20, map);
		gameMap.at(x, y).setGround(crop);
	}
}
