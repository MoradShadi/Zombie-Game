package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents bare dirt.
 */
public class Crop extends Ground {
	private int turn;
	private boolean riped;
	private Location[][] map;
	private int x;
	private int y;
	
	public Crop(int turns, Location location) {
		super('^');
		this.turn = turns;
		map[x][y] = location;
	}
	
	public void boostRipen() {
		turn -= 10;
	}
	
	public void normalRipen() {
		turn -= 1;
	}
	
	public Location getLocation() {
		return map[x][y];
	}
	
	public boolean isRipe() {
		if (turn <= 0) {
			riped = true;
		}
		return riped;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
}
