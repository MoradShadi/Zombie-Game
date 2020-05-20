package game;

import edu.monash.fit2099.engine.GameMap;
public class SowCrop {
	
	public SowCrop(int x, int y, GameMap gameMap) {
		Crop crop = new Crop();
		gameMap.at(x, y).setGround(crop);
	}
}
