package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class Harvest {
	Location map;
	
	public void farmerHarvest(Crop crop, GameMap gameMap) {
		crop.setTurn(20);
		map = crop.getLocation();
		
		Food food = new Food();
		}
	
	
	public void playerHarvest(Crop crop, GameMap gameMap) {
		crop.setTurn(20);
		map = crop.getLocation();
	}
}