package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents a crop.
 */
public class Crop extends Ground {
	private int turnCount;
	private int turnsNeeded;
	
	/**
	 * Constructor.
	 */
	public Crop() {
		super('^');
		turnCount = 0;
		turnsNeeded = 20;
	}
	
	/**
	 * Fertilize the crop to boost its turn by 10 turns.
	 */
	public void fertilize() {
		if (!isRipe()) {
			turnCount += 10;
			if (turnCount > turnsNeeded) {
				turnCount = turnsNeeded;
			}
		}
	}
	
	/**
	 * Override method. Increment the turn normally if it's not ripe yet
	 *
	 * @param location Location of the crop
	 */
	@Override
	public void tick(Location location) {
		if (!isRipe()) {
			turnCount++;
		}
	}
	
	/**
	 * Returns "true" if the crop is ripe or false if else.
	 *
	 * @return true if the crop is ripe or false if else.
	 */
	public boolean isRipe() {
		return turnCount == turnsNeeded;
	}
	
}
