package game;

/**
 * Base class for all kinds of gun ammo. Each ammo item will have a corresponding gun that it is compatible with and also
 * a reload amount which represents the number of bullets in the ammo pack.
 * 
 * @author User
 *
 */
public abstract class Ammo extends PortableItem {
	private String compatibleGun;
	private int reloadAmount;

	public Ammo(String name, char displayChar, String gun, int initReloadAmount) {
		super(name, displayChar);
		// TODO Auto-generated constructor stub
		this.compatibleGun = gun;
		this.reloadAmount = initReloadAmount;
	}

	/**
	 * Accessor for the name of the gun that the ammo is for
	 */
	public String getGun() {
		return this.compatibleGun;
	}
	
	/**
	 * Accessor for the reload amount in the ammo pack
	 */
	public int getReloadAmount() {
		return this.reloadAmount;
	}
}
