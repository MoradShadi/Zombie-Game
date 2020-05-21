package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents a crop.
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
		x = location.x();
		y = location.y();
	}
	
	public int boostRipen() {
		turn -= 10;
		return turn;
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
