package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Menu;

public class ShootingSubmenu extends Action {
	Gun gun;
	Menu submenu;
	Display display;

	public ShootingSubmenu(Gun initGun, Display initDisplay) {
		// TODO Auto-generated constructor stub
		this.gun = initGun;
		this.submenu = new Menu();
		this.display = initDisplay;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		Actions actions = new Actions();
		Action chosenShot = null;
		
		if (gun.hasCapability(GunTargetCapability.DIRECTIONAL)) {
			Location actorLocation = map.locationOf(actor);
			for (Exit exit : actorLocation.getExits()) {
				String direction = exit.getName();
				int range = this.gun.getRange();
				actions.add(new DirectionalShootingAction(range, direction));
			}
			
			System.out.println(" Choose which direction to shoot: ");
			chosenShot = this.submenu.showMenu(actor, actions, this.display);
		}
		return chosenShot.menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " shoots using " + gun;
	}

}
