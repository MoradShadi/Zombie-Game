package game;

import java.util.Random;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * Class representing the Player.
 */
public class Farmer extends Human {
	private Random rand = new Random();
	private Location[][] map;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Farmer(String name) {
		super(name, 'F', 50);
	}
	
	public void sowChance(int x, int y, GameMap gameMap) {
		// location for left
		int x_left = x - 1;
		int x_right = x + 1;
			
		Ground ground_l = map[x_left][y].getGround();
		Ground ground_r = map[x_right][y].getGround();
			
		if(ground_l.getDisplayChar() == '.') {
			double sowChance = rand.nextDouble();

			if (sowChance <= 0.33) {
				new SowCrop(x_left, y, gameMap);
			}
		}
		
		if(ground_r.getDisplayChar() == '.') {
			double sowChance = rand.nextDouble();

			if (sowChance <= 0.33) {
				new SowCrop(x_left, y, gameMap);
			}	
		}
	}
	
	public void fertilizeCrop(int x, int y, GameMap gameMap) {
		Ground ground = map[x][y].getGround();
		if(ground.getDisplayChar() == '^') {
			Crop crop = (Crop) ground;
			if(crop.isRipe() == false) {
				crop.boostRipen();
			}
		}
		
		double sowingChance = rand.nextDouble();
		
		if (sowingChance <= 0.33) {
			new SowCrop(x, y, gameMap);
		}	
	}
}
