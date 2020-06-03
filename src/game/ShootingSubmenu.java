package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Menu;

public class ShootingSubmenu extends Action {
	Gun gun;
	Menu submenu;

	public ShootingSubmenu(Gun initGun) {
		// TODO Auto-generated constructor stub
		this.gun = initGun;
		this.submenu = new Menu();
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		if (gun.hasCapability(GunTargetCapability.DIRECTIONAL)) {
			Location actorLocation = map.locationOf(actor);
			
		}
		return null;
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " shoots using " + gun;
	}

}
