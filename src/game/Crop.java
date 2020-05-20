package game;

import edu.monash.fit2099.engine.Ground;

/**
 * A class that represents bare dirt.
 */
public class Crop extends Ground {
	int turn;
	boolean riped;
	
	public Crop(int turns) {
		super('^');
		this.turn = turns;
	}
	
	public void boostRipen() {
		turn -= 10;
		if (turn <= 0) {
			riped = true;
		}
	}
	
	public void normalRipen() {
		turn -= 1;
		if (turn <= 0) {
			riped = true;
		}
	}
}
