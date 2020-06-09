package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.World;

public class MyWorld extends World {

	public MyWorld(Display display) {
		super(display);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean stillRunning() {
		boolean playerAlive = actorLocations.contains(player);
		boolean zombiesRemaining = false;
		for (Actor actor : actorLocations) {
			if (actor instanceof Zombie || actor instanceof MamboMarie) {
				zombiesRemaining = true;
			}
		}
		
		return playerAlive && zombiesRemaining;
	}

}
