package game;

public abstract class Ammo extends PortableItem {
	private String compatibleGun;
	private int reloadAmount;

	public Ammo(String name, char displayChar, String gun, int initReloadAmount) {
		super(name, displayChar);
		// TODO Auto-generated constructor stub
		this.compatibleGun = gun;
		this.reloadAmount = initReloadAmount;
	}

	public String getGun() {
		return this.compatibleGun;
	}
	
	public int getReloadAmount() {
		return this.reloadAmount;
	}
}
