package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

public class MamboMarie extends ZombieActor {
	
	private static int MamboMarieturnCount;
	private Behaviour[] behaviours = {
			new ChantingBehaviour(),
			new WanderBehaviour()
	};

	public MamboMarie(String name) {
		super(name,'M',100, ZombieCapability.ALIVE);
		MamboMarieturnCount = 0;
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (map.contains(this)){
			MamboMarieturnCount++;
		}
		if (MamboMarieturnCount % 10 == 0 && MamboMarieturnCount != 30) {
			Action action = behaviours[0].getAction(this, map);
			if (action != null) {
				return action;
			}
		}
		Action action = behaviours[1].getAction(this, map);
		if (action != null) {
			return action;
		}
		return new DoNothingAction();
	}
}
