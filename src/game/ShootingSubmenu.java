package game;

import java.util.ArrayList;

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
		Actions possibleShots = new Actions();
		Action chosenAction;
		if (gun.hasCapability(GunTargetCapability.DIRECTIONAL)) {
			Location actorLocation = map.locationOf(actor);
			for (Exit exit : actorLocation.getExits()) {
				possibleShots.add(new DirectionalShootingAction(this.gun, exit));
			}
			
			System.out.println("+ Choose which direction to shoot + ");
			chosenAction = this.submenu.showMenu(actor, possibleShots, this.display);
			
			return chosenAction.execute(actor, map);
		}
		else if (gun.hasCapability(GunTargetCapability.SINGLE_TARGET)) {
			Sniper sniper = (Sniper) gun;
			//Let player choose if he wants to aim or fire the gun
			System.out.println("+ Choose aim or fire +");
			System.out.println("1: Aim " + this.gun + System.lineSeparator() + "2: Fire" + this.gun);
			char key;
			do {
				key = display.readChar();
			} while (key != '1' && key != '2');
			
			//Get a list of all zombies on the map
			ArrayList<Zombie> targetZombies = new ArrayList<Zombie>();
			for (int x : map.getXRange()) {
				for (int y : map.getYRange()) {
					Actor target = map.getActorAt(map.at(x, y));
					if (target.hasCapability(ZombieCapability.UNDEAD)) {
						targetZombies.add((Zombie) target);
					}
				}
			}
			
			//Use another submenu to let player choose which zombie as target
			Actions possibleActions = new Actions();
			if (key == '1') {
				for (Zombie targetZombie : targetZombies) {
					possibleActions.add(new AimingAction(sniper, targetZombie));
				}
			}
			
			System.out.println("+ Choose which zombie +");
			chosenAction = this.submenu.showMenu(actor, possibleActions, display);
			
			return chosenAction.execute(actor, map);
		}
		return null;
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " shoots using " + gun;
	}

}
