package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.PickUpItemAction;

public class Harvest implements Behaviour {
	Location location;
	Location[][] map;
	
	public Action farmerHarvest(Crop crop, GameMap gameMap) {
		crop.setTurn(20);
		
		Food food = new Food();
		return food.getDropAction();
		}

	public Action playerHarvest(Player player, Crop crop, GameMap gameMap) {
		crop.setTurn(20);
		
		Item food = new Food();
		
		return new PickUpItemAction(food);
	}


	@Override
	public Action getAction(Actor actor, GameMap gameMap) {
		// TODO Auto-generated method stub
		location = gameMap.locationOf(actor);
		if(actor.getDisplayChar() == 'F') {
			int x_left = location.x() - 1;
			int x_right = location.x() + 1;
			int x = location.x();
			int y = location.y();
			
			Ground ground_l = map[x_left][y].getGround();
			Ground ground_r = map[x_right][y].getGround();
				
			if(ground_l.getDisplayChar() == '^') {
				Crop crop = (Crop) ground_l;
				this.farmerHarvest(crop, gameMap);
				crop.setTurn(20);
			}
			
			if(ground_r.getDisplayChar() == '^') {
				Crop crop = (Crop) ground_r;
				this.farmerHarvest(crop, gameMap);
				crop.setTurn(20);
			}
			
			Ground ground = map[x][y].getGround();
			
			if (ground.getDisplayChar() == '^') {
				Crop crop = (Crop) ground;
				crop.setTurn(20);
				return this.farmerHarvest(crop, gameMap);
			}
		}
		return getAction(null, null);
	}
}