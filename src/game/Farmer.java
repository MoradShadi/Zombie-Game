package game;

import java.util.Random;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Class representing the Player.
 */
public class Farmer extends Human {
	private Random rand = new Random();
	private double sowingChance = rand.nextDouble();
	private Crop crop;
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
		if(map[x][y].equals(crop.getLocation()))
			if (sowingChance <= 0.33) {
				new SowCrop(x, y, gameMap);
			}
	}
}
