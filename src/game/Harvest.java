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
	
	public void farmerHarvest(Crop crop, GameMap gameMap) {
		crop.setTurn(20);
		
		Food food = new Food();
		
		System.out.println("A crop is harvested.");
		
		food.getDropAction();
		
		}

	public void playerHarvest(Player player, Crop crop, GameMap gameMap) {
		crop.setTurn(20);
		
		Item food = new Food();
		
		System.out.println("PLayer " + player + " harvested a crop.");
		
		new PickUpItemAction(food);
	}


	@Override
	public Action getAction(Actor actor, GameMap gameMap) {
		// TODO Auto-generated method stub
		if(actor.getDisplayChar() == 'F') {
			Farmer farmer = (Farmer) actor;
			location = gameMap.locationOf(farmer);
			
			int x_left = location.x() - 1;
			int x_right = location.x() + 1;
			int x = location.x();
			int y = location.y();
			
			if(x_left >= 0) {
				Ground ground_l = gameMap.at(x_left, y).getGround();
				
				if(ground_l.getDisplayChar() == '^') {
					Crop crop = (Crop) ground_l;
					crop.setTurn(20);
					farmerHarvest(crop, gameMap);
				}
			}
			
			if(x_right >= 0) {
				Ground ground_r = gameMap.at(x_right, y).getGround();
				
				if(ground_r.getDisplayChar() == '^') {
					Crop crop = (Crop) ground_r;
					crop.setTurn(20);
					farmerHarvest(crop, gameMap);
				}
			}
			
			Ground ground = gameMap.at(x, y).getGround();
			
			if (ground.getDisplayChar() == '^') {
				Crop crop = (Crop) ground;
				crop.setTurn(20);
				farmerHarvest(crop, gameMap);
			}
		}
		
		else {
			Player player = (Player) actor;
			location = gameMap.locationOf(player);
			
			int x_left = location.x() - 1;
			int x_right = location.x() + 1;
			int x = location.x();
			int y = location.y();
			
			if(x_left >= 0) {
				Ground ground_l = gameMap.at(x_left, y).getGround();
				
				if(ground_l.getDisplayChar() == '^') {
					Crop crop = (Crop) ground_l;
					if(crop.isRipe()) {
						playerHarvest(player, crop, gameMap);
					}
				}
			}
			
			if(x_right >= 0) {
				Ground ground_r = gameMap.at(x_right, y).getGround();
				
				if(ground_r.getDisplayChar() == '^') {
					Crop crop = (Crop) ground_r;
					if(crop.isRipe()) {
						playerHarvest(player, crop, gameMap);
					}
				}
			}
			
			Ground ground = gameMap.at(x, y).getGround();
			
			if (ground.getDisplayChar() == '^') {
				Crop crop = (Crop) ground;
				if(crop.isRipe()) {
					playerHarvest(player, crop, gameMap);
				}
			}
		}
		return null;
	}
}