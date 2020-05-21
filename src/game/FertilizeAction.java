package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class FertilizeAction extends Action {
	Crop cropToBeFertilized;

	public FertilizeAction(Crop initCrop) {
		// TODO Auto-generated constructor stub
		cropToBeFertilized = initCrop;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		cropToBeFertilized.fertilize();
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " fertilized a crop.";
	}

}
