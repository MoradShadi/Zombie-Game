package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

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
	
	/**
     * Inform a carried Item of the passage of time.
     * 
     * This method is called once per turn, if the Item is being carried.
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
	@Override
	public void tick(Location currentLocation, Actor actor) {

		for (Item item : new ArrayList<Item>(actor.getInventory())) {
			boolean itemIsGun = item.hasCapability(GunTargetCapability.DIRECTIONAL) || item.hasCapability(GunTargetCapability.SINGLE_TARGET);
			if (itemIsGun) {
				Gun gun = (Gun) item;
				if (this.getGun() == gun.toString()) {
					gun.reload(this.getReloadAmount());
					actor.removeItemFromInventory(this);
					System.out.println(actor + " reloads " + gun.toString() + " with " + this.getReloadAmount() + " ammo");
				}
			}
		}
	}
}
