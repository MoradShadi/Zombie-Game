package game;

import java.util.Random;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class SowCrop implements Behaviour {
	Location location;
	Location [][] map;
	private Random rand = new Random();
	
	public void sow(int x, int y, GameMap gameMap) {
		Crop crop = new Crop(20, location);
		gameMap.at(x, y).setGround(crop);
	}

	@Override
	public Action getAction(Actor actor, GameMap gameMap) {
		// TODO Auto-generated method stub
		location = gameMap.locationOf(actor);
		int x = location.x();
		int y = location.y();
		int x_left = x - 1;
		int x_right = x + 1;
		System.out.println(x);
			
		Ground ground_l = map[x_left][y].getGround();
		Ground ground_r = map[x_right][y].getGround();
			
		if(ground_l.getDisplayChar() == '.') {
			double sowChance = rand.nextDouble();

			if (sowChance <= 0.33) {
				this.sow(x_left, y, gameMap);
			}
		}
		
		if(ground_r.getDisplayChar() == '.') {
			double sowChance = rand.nextDouble();

			if (sowChance <= 0.33) {
				sow(x_left, y, gameMap);
			}	
		}
		return null;
	}
}
