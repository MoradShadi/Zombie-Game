package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class SowCropAction extends Action {
	private Location cropLocation;

	public SowCropAction(Location initCropLocation) {
		// TODO Auto-generated constructor stub
		this.cropLocation = initCropLocation;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		cropLocation.setGround(new Crop());
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " planted a crop.";
	}

}
