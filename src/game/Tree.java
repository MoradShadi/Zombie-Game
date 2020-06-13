package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A tree that starts as a sapling and grows into a large tree.
 * 
 * @author ram
 *
 */
public class Tree extends Ground {
	private int age = 0;

	public Tree() {
		super('+');
	}

	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		if (age == 10)
			displayChar = 't';
		if (age == 20)
			displayChar = 'T';
	}
	
	/**
	 * Checks if the tree is old enough to be chopped for planks
	 * 
	 * @return true if the tree is old enough
	 */
	public boolean canCut() {
		if (this.age >= 20) {
			return true;
		}
		return false;
	}
}
