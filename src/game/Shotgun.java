package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Display;

public class Shotgun extends Gun {

	public Shotgun() {
		super("shotgun", 'S', 25, false, 3);
		// TODO Auto-generated constructor stub
	}

	public Action getShootAction(Display display) {
		return null;
	}
}
