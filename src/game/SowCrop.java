package game;

import java.util.ArrayList;
import java.util.Random;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class SowCrop implements Behaviour {
	ArrayList<Crop> crops = new ArrayList<>();
	Location location;
	Location [][] map;
	private Random rand = new Random();
	
	public void sow(int x, int y, GameMap gameMap) {
		Crop crop = new Crop(20, location);
		crops.add(crop);
		gameMap.at(x, y).setGround(crop);
		System.out.println("A farmer has sowed a crop.");
	}

	@Override
	public Action getAction(Actor actor, GameMap gameMap) {
		// TODO Auto-generated method stub
		Farmer farmer = (Farmer) actor;
		location = gameMap.locationOf(farmer);
		int x = location.x();
		int y = location.y();
		int x_left = x - 1;
		int x_right = x + 1;
		
		if(x_left >= 0) {
			Ground ground_l = gameMap.at(x_left, y).getGround();
			
			if(ground_l.getDisplayChar() == '.') {
				double sowChance = rand.nextDouble();

				if (sowChance <= 0.33) {
					this.sow(x_left, y, gameMap);
				}
			}
		}
		
		if(x_right >= 0) {
			Ground ground_r = gameMap.at(x_right, y).getGround();
			
			if(ground_r.getDisplayChar() == '.') {
				double sowChance = rand.nextDouble();

				if (sowChance <= 0.33) {
					sow(x_right, y, gameMap);
				}	
			}
		}
		return null;
	}
}
