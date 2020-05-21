package game;

import java.util.ArrayList;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;

/**
 * Class representing the Farmer.
 */
public class Farmer extends Human {
	private ArrayList<SowCrop> sowCrops = new ArrayList<>();
	private Behaviour[] behaviours = {
			new Harvest(),
			new SowCrop(),
			new Fertilize(),
			new WanderBehaviour()
	};

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Farmer(String name) {
		super(name, 'F', 50);
	}
	
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		for(SowCrop sowCrop: sowCrops) {
			for(Crop crop: sowCrop.crops) {
				if(!crop.isRipe()) {
					crop.normalRipen();
				}
			}
		}
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null) {
				return action;
			}
		}
		return null;
	}
}
