package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents a crop.
 */
public class Crop extends Ground {
	private int turnCount;
	private int turnsNeeded;
	
	public Crop() {
		super('^');
		turnCount = 0;
		turnsNeeded = 20;
	}
	
	public void fertilize() {
		if (!isRipe()) {
			turnCount += 10;
			if (turnCount > turnsNeeded) {
				turnCount = turnsNeeded;
			}
		}
	}
	
	@Override
	public void tick(Location location) {
		if (!isRipe()) {
			turnCount++;
		}
	}
	
	public boolean isRipe() {
		return turnCount == turnsNeeded;
	}
	
}
