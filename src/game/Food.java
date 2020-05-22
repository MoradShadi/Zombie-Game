package game;

/**
 * A class that represents food.
 */
public class Food extends PortableItem {
	private int healAmount;
	
	/**
	 * Constructor.
	 * 
	 */
	public Food(int initHealAmount) {
		super("food", 'o');
		healAmount = initHealAmount;
	}
	
	public int getHealAmount() {
		return healAmount;
	}
}
