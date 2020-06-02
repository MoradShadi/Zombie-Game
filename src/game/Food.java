package game;

/**
 * A class that represents food.
 */
public class Food extends PortableItem {
	private int healAmount;
	
	/**
	 * Constructor
	 * 
	 * @param initHealAmount amount of healing if this food is eaten
	 */
	public Food(int initHealAmount) {
		super("food", 'o');
		healAmount = initHealAmount;
	}
	
	public int getHealAmount() {
		return healAmount;
	}
}
